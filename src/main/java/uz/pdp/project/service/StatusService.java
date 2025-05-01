package uz.pdp.project.service;

import uz.pdp.project.entity.Status;
import java.util.List;

public interface StatusService {
    List<Status> getAll();
    List<Status> getActiveStatusesOrdered();
    Status getById(Integer id);
    void save(Status status);
    void updateAll(List<Status> statuses);

}
