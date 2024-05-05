package uz.sardorbroo.secretarybot.service.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDate;

@Data
@Accessors(chain = true)
public class EventDateDTO {

    private LocalDate date;

}
