package com.example.demo.Service;

import com.example.demo.BlobsAzure.BlobStorageService;
import com.example.demo.DTO.ProdutoDTO;
import com.example.demo.Model.ProdutoModel;
import com.example.demo.Model.URLImagensModel;
import com.example.demo.Repository.ProdutoRepository;
import com.example.demo.Repository.URLImagensRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.logging.Logger;

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
        for (MultipartFile imagem : dto.getImagens()) {
            saveImage(imagem, produtoModel, false);
        }

        return new ResponseEntity<>(dto, HttpStatus.CREATED);
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