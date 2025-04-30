package uz.pdp.project.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.project.entity.Status;
import uz.pdp.project.repo.StatusRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StatusService {
    private final StatusRepository statusRepository;

    public List<Status> getAll() {
        return statusRepository.findAll();
    }

    public List<Status> getActiveStatusesOrdered() {
        return statusRepository.findAllByActiveTrueOrderByPositionNumber();
    }

    public void save(Status status) {
        statusRepository.save(status);
    }


}
