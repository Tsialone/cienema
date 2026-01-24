package com.cinema.dev.forms;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CommandeForm {

    private Long idSociete;

    // list of diffusion ids selected in the form
    private List<Long> diffusionIds = new ArrayList<>();

    // for new rows (if diffusion doesn't exist yet)
    private List<Long> seanceIds = new ArrayList<>();
    private List<Long> pubIds = new ArrayList<>();

}
