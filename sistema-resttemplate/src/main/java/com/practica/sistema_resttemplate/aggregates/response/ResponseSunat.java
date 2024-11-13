package com.practica.sistema_resttemplate.aggregates.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseSunat {
    private String razonSocial;
    private String tipoDocumento;
    private String numeroDocumento;
    private String estado;
    private String condicion;
    private String direccion;
    private String ubigeo;
    private String viaTipo;
    private String viaNombre;
    private String zonaCodigo;
    private String zonaTipo;
    private String numero;
    private String interior;
    private String lote;
    private String dpto;
    private String manzana;
    private String kilometro;
    private String distrito;
    private String provincia;
    private String departamento;
    private boolean EsAgenteRetencion;

    @Override
    public String toString(){
        return "ResponseSunat{"+
                "razonSocial ='" + razonSocial + '\'' +
                ", tipoDocumento='" + tipoDocumento + '\'' +
                ", numeroDocumento='"+ numeroDocumento + '\'' +
                ", estado='" +estado + '\'' +
                ", condicion='" + condicion + '\'' +
                ", direccion='" + direccion + '\'' +
                ", ubigeo='" + ubigeo + '\'' +
                ", viaTipo='" + viaTipo + '\'' +
                ", viaNombre='" + viaNombre + '\'' +
                ", zonaCodigo='" + zonaCodigo + '\'' +
                ", zonaTipo='" + zonaTipo + '\'' +
                ", numero='" + numero + '\'' +
                ", interior='" + interior + '\'' +
                ", lote='" + lote + '\'' +
                ", dpto='" + dpto + '\'' +
                ", manzana='" + manzana + '\'' +
                ", kilometro='" + kilometro + '\'' +
                ", distrito='" + distrito + '\'' +
                ", provincia='" + provincia + '\'' +
                ", departamento='" + departamento + '\'' +
                "}";
    }
}