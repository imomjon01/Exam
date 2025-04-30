package uz.pdp.project.dto;

import lombok.Data;
import uz.pdp.project.entity.Status;

import java.util.List;

@Data
public class StatusListWrapper {
    private List<Status> statusList;
}

