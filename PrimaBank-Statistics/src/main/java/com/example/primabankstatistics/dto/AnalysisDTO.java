package com.example.primabankstatistics.dto;

import java.util.Objects;

public class AnalysisDTO {
    private Long idClient;
    private ProductDTO product;

    public AnalysisDTO() {
    }

    public AnalysisDTO(Long idClient, ProductDTO product) {
        this.idClient = idClient;
        this.product = product;
    }

    public Long getIdClient() {
        return idClient;
    }

    public void setIdClient(Long idClient) {
        this.idClient = idClient;
    }

    public ProductDTO getProduct() {
        return product;
    }

    public void setProduct(ProductDTO product) {
        this.product = product;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnalysisDTO that = (AnalysisDTO) o;
        return Objects.equals(idClient, that.idClient) && Objects.equals(product, that.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idClient, product);
    }

    @Override
    public String toString() {
        return "AnalysisDTO{" +
                "idClient='" + idClient + '\'' +
                ", product=" + product +
                '}';
    }
}
