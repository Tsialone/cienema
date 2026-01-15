// Classe pour la clé composite

package com.cinema.dev.models;  
import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public
class CategoriePrixId implements Serializable {
    private Long categoriePlace;
    private Long prixC;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CategoriePrixId that = (CategoriePrixId) o;
        return categoriePlace.equals(that.categoriePlace) && prixC.equals(that.prixC);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(categoriePlace, prixC);
    }
}