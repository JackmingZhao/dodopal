package com.dodopal.api.product.dto;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Order")
public class LoadOrdersResponseDTO {
    @XmlElement(name = "LoadOrder")
    private List<LoadOrderResponseDTO2> loadOrders;

    public LoadOrdersResponseDTO() {

    }

    public LoadOrdersResponseDTO(List<LoadOrderResponseDTO2> loadOrders) {
        this.loadOrders = loadOrders;
    }

    public List<LoadOrderResponseDTO2> getLoadOrders() {
        if(loadOrders == null) {
            loadOrders = new ArrayList<LoadOrderResponseDTO2>();
        }
        return loadOrders;
    }
}
