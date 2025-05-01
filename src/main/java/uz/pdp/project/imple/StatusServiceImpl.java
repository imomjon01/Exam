package uz.pdp.project.imple;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import uz.pdp.project.entity.Status;
import uz.pdp.project.repo.StatusRepository;

import jakarta.persistence.EntityNotFoundException;
import uz.pdp.project.service.StatusService;

import java.util.List;

@Service                         // ← only on the impl class
@RequiredArgsConstructor
public class StatusServiceImpl implements StatusService {

    private final StatusRepository statusRepository;

    @Override
    public List<Status> getAll() {
        return statusRepository.findAll();
    }

    @Override
    public List<Status> getActiveStatusesOrdered() {
        // your custom finder or a simple sort:
        return statusRepository.findAllByActiveTrueOrderByPositionNumber();
        // or: return statusRepository.findAll(Sort.by("id"));
    }

    @Override
    public Status getById(Integer id) {
        return statusRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Status not found: " + id));
    }

    @Override
    public void save(Status status) {
        statusRepository.save(status);
    }

    @Override
    public void updateAll(List<Status> statuses) {
        statusRepository.saveAll(statuses);
    }

    @Override
    public void saveAll(List<Status> statuses) {
        statusRepository.saveAll(statuses);
    }
}
