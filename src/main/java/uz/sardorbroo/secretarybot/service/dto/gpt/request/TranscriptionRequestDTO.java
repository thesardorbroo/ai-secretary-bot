package uz.sardorbroo.secretarybot.service.dto.gpt.request;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.File;

@Data
@Accessors(chain = true)
public class TranscriptionRequestDTO {

    private File file;

    private String model;

}
