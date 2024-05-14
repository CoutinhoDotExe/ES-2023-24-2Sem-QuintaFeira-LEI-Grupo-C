package pt.iscte_iul.ista.ES_2324_LEI_GC;

import java.util.List;

public class Room {
    private String edificio;
    private String nomeSala;
    private List<String> caracteristicas;

    public Room(String edificio, String nomeSala, List<String> caracteristicas) {
        this.edificio = edificio;
        this.nomeSala = nomeSala;
        this.caracteristicas = caracteristicas;
    }

    public String getEdificio() {
        return edificio;
    }

    public String getNomeSala() {
        return nomeSala;
    }

    public List<String> getCaracteristicas() {
        return caracteristicas;
    }
    public String getCarateristica() {
        StringBuilder builder = new StringBuilder();
        for (String carateristica : caracteristicas) {
            builder.append(carateristica).append(", ");
        }
        // Remove the last comma and space if there are carateristicas
        if (builder.length() > 0) {
            builder.setLength(builder.length() - 2);
        }
        return builder.toString();
    }
}
