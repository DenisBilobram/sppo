package is.lab1.is_lab1.controller.request;

import java.time.format.DateTimeFormatter;
import java.util.List;

import is.lab1.is_lab1.model.ImportAction;
import is.lab1.is_lab1.model.ImportActionStatus;
import is.lab1.is_lab1.model.ObjectType;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ImportActionDto {

    private Long id;

    private ObjectType importActionType;

    private List<HumanBeingDto> humanBeings;

    private List<CarDto> cars;

    private List<CoordinatesDto> coordinates;

    private String creationDate;

    private String isUser;

    private Integer count;

    private String fileName;

    private ImportActionStatus status;

    public ImportActionDto(ImportAction action) {
        this.id = action.getId();
        this.importActionType = action.getImportActionType();
        this.humanBeings = action.getHumanBeings().stream().map(el -> new HumanBeingDto(el)).toList();
        this.coordinates = action.getCoordinates().stream().map(el -> new CoordinatesDto(el)).toList();
        this.creationDate = action.getCreationDate().format(DateTimeFormatter.ofPattern("d MMM uuuu"));
        this.cars = action.getCars().stream().map(el -> new CarDto(el)).toList();
        this.isUser = action.getIsUser().getUsername();
        this.count = action.getCount();
        this.fileName = action.getFileName();
        this.status = action.getStatus();
    }

}