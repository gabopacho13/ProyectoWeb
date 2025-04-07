package co.edu.javeriana.jpa_example2.mapper;

import co.edu.javeriana.jpa_example2.dto.ProductoCiudadDTO;
import co.edu.javeriana.jpa_example2.model.ProductoCiudad;

public class ProductoCiudadMapper {

    // Convierte de entidad a DTO
    public static ProductoCiudadDTO toDTO(ProductoCiudad productoCiudad) {
        if (productoCiudad == null) {
            return null;
        }
        return new ProductoCiudadDTO(
            productoCiudad.getId(),
            productoCiudad.getFactor_demanda(),
            productoCiudad.getFactor_oferta(),
            productoCiudad.getStock()
        );
    }

    // Convierte de DTO a entidad
    public static ProductoCiudad toEntity(ProductoCiudadDTO productoCiudadDTO) {
        if (productoCiudadDTO == null) {
            return null;
        }
        ProductoCiudad productoCiudad = new ProductoCiudad();
        productoCiudad.setFactor_demanda(productoCiudadDTO.getFactorDemanda());
        productoCiudad.setFactor_oferta(productoCiudadDTO.getFactorOferta());
        productoCiudad.setStock(productoCiudadDTO.getStock());
        return productoCiudad;
    }
}