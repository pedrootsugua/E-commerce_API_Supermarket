package com.example.demo.Service;

import com.example.demo.BlobsAzure.BlobStorageService;
import com.example.demo.DTO.*;
import com.example.demo.Model.ProdutoModel;
import com.example.demo.Model.URLImagensModel;
import com.example.demo.Model.UsuarioModel;
import com.example.demo.Repository.ProdutoRepository;
import com.example.demo.Repository.URLImagensRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class ProdutoService {

    private static final Logger logger = Logger.getLogger(ProdutoService.class.getName());

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private URLImagensRepository urlImagensRepository;

    @Autowired
    private BlobStorageService blobStorageService;

    public ResponseEntity<ProdutoDTO> cadastrarProduto(ProdutoDTO dto) throws Exception {
        ProdutoModel produtoModel = new ProdutoModel(dto);
        produtoModel.setAtivo(true);
        produtoModel = produtoRepository.save(produtoModel);

        // Salvando a imagem principal
        if (dto.getImagemPrincipal() != null) {
            logger.info("Salvando a imagem principal");
            saveImage(dto.getImagemPrincipal(), produtoModel, true);
        } else {
            logger.warning("Imagem principal não fornecida");
        }

        // Salvando as imagens adicionais
        if (dto.getImagens() != null && !dto.getImagens().isEmpty()) {
            for (MultipartFile imagem : dto.getImagens()) {
                if (imagem != null && !imagem.isEmpty()) {
                    saveImage(imagem, produtoModel,false);
                } else {
                    logger.warning("Imagem adicional fornecida está vazia ou nula");
                }
            }
        } else {
            logger.info("Nenhuma imagem adicional fornecida");
        }

        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }


    public ResponseEntity<Map<String, Object>> listarProdutos(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));
        Page<ProdutoModel> produtoPage = produtoRepository.findAll(pageable);
        List<ProdutoRetornoDTO> produtos = produtoPage.stream()
                .map(produto -> new ProdutoRetornoDTO(
                        produto.getId(),
                        produto.getNomeProduto(),
                        produto.getPreco(),
                        produto.getQuantidade(),
                        produto.getAtivo(),
                        produto.getUrlImagensModels()
                ))
                .collect(Collectors.toList());
        Map<String, Object> response = new HashMap<>();
        response.put("produtos", produtos);
        response.put("totalPages", produtoPage.getTotalPages());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public ResponseEntity<ProdutoAlterarResponseDTO> buscarProduto(Long id) {

        ProdutoModel produtoModel = produtoRepository.findById(id).orElse(null);
        if (produtoModel == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        ProdutoAlterarResponseDTO produtoAlterarResponseDTO = new ProdutoAlterarResponseDTO(produtoModel);
        return new ResponseEntity<>(produtoAlterarResponseDTO, HttpStatus.OK);
    }

    public ResponseEntity<Map<String, Object>> buscarProdutoPorNome(String nome, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));
        Page<ProdutoModel> produtoPage = produtoRepository.buscaPorNome(nome, pageable);
        if (produtoPage.getContent().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<ProdutoRetornoDTO> produtos = produtoPage.stream()
                .map(produto -> new ProdutoRetornoDTO(
                        produto.getId(),
                        produto.getNomeProduto(),
                        produto.getPreco(),
                        produto.getQuantidade(),
                        produto.getAtivo(),
                        produto.getUrlImagensModels()
                ))
                .collect(Collectors.toList());
        Map<String, Object> response = new HashMap<>();
        response.put("produtos", produtos);
        response.put("totalPages", produtoPage.getTotalPages());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public ResponseEntity<ProdutoAlterarResponseDTO> alterarProduto(ProdutoAlterarRequestDTO dto) throws Exception {
        ProdutoModel produtoSalvo = produtoRepository.findById(dto.getId()).orElseThrow(
                () -> new RuntimeException("Produto não encontrado!"));
        produtoSalvo.setNomeProduto(dto.getNomeProduto());
        produtoSalvo.setDescricao(dto.getDescricao());
        produtoSalvo.setPreco(dto.getPreco());
        produtoSalvo.setQuantidade(dto.getQuantidade());
        produtoSalvo.setCategoria(dto.getCategoria());
        produtoSalvo.setMarca(dto.getMarca());
        produtoSalvo.setAvaliacao(dto.getAvaliacao());
        produtoRepository.save(produtoSalvo);
        if (!dto.getUrlImagensExcluidas().isEmpty()) {
            for (String url : dto.getUrlImagensExcluidas()){
                URLImagensModel imagemSalva = urlImagensRepository.findByUrl(url);
                urlImagensRepository.delete(imagemSalva);
                blobStorageService.deleteImage(url);
            }
        }
        if (dto.getImagemPrincipal() != null) {
            try {
                URLImagensModel imagemPrincipal = urlImagensRepository.findPadrao(dto.getId());
                urlImagensRepository.delete(imagemPrincipal);
                saveImage(dto.getImagemPrincipal(), produtoSalvo, true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (dto.getImagensNovas() != null && !dto.getImagensNovas().isEmpty()) {
            for (MultipartFile imagem : dto.getImagensNovas()) {
                if (imagem != null && !imagem.isEmpty()) {
                    saveImage(imagem, produtoSalvo,false);
                } else {
                    logger.warning("Imagem adicional fornecida está vazia ou nula");
                }
            }
        } else {
            logger.info("Nenhuma imagem adicional fornecida");
        }

        ProdutoModel produtoAtualizado = produtoRepository.findById(dto.getId()).orElseThrow(
                () -> new Exception("Produto não encontrado"));
        ProdutoAlterarResponseDTO produtoAlterado = new ProdutoAlterarResponseDTO(produtoAtualizado);

        return new ResponseEntity<>(produtoAlterado, HttpStatus.OK);
    }

    private void saveImage(MultipartFile imagem, ProdutoModel produtoModel, boolean isPrincipal) throws Exception {
        if (imagem != null && !imagem.isEmpty()) {
            String imageUrl = blobStorageService.uploadImage(imagem);
            URLImagensModel urlImagensModel = new URLImagensModel();
            urlImagensModel.setUrl(imageUrl);
            urlImagensModel.setPadrao(isPrincipal);
            urlImagensModel.setProdutoId(produtoModel);
            urlImagensRepository.save(urlImagensModel);
        } else {
            logger.warning("Imagem fornecida está vazia ou nula");
        }
    }
}
