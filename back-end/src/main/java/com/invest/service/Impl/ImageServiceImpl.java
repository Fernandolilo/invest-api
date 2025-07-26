package com.invest.service.Impl;

import java.util.Base64;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.invest.dto.ClientNewDTO;
import com.invest.entity.Client;
import com.invest.service.ImageService;

@Service
public class ImageServiceImpl  implements ImageService{

	@Override
    public void processarSelfie(ClientNewDTO cli, Client entity) {
        if (cli.getSelfie() != null && cli.getSelfie().startsWith("data:")) {
            String[] parts = cli.getSelfie().split(",");
            if (parts.length == 2) {
                String metadata = parts[0];
                String base64Data = parts[1];
                String tipoImagem = metadata.substring(metadata.indexOf(":") + 1, metadata.indexOf(";"));
                byte[] imagemBytes = Base64.getDecoder().decode(base64Data);
                entity.setImagem(imagemBytes);
                entity.setImagemTipo(tipoImagem);
                entity.setImagemNome("selfie_" + UUID.randomUUID() + "." + tipoImagem.split("/")[1]);
            }
        }
    }

}
