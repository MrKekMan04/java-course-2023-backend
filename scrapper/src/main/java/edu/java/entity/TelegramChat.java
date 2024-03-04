package edu.java.entity;

import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class TelegramChat {
    private Long id;
    private List<Link> links;
}
