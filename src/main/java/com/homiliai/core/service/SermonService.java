package com.homiliai.core.service;

import com.homiliai.core.dto.CreateSermonDTO;
import com.homiliai.core.entity.Occasion;
import com.homiliai.core.entity.Sermon;
import com.homiliai.core.entity.Sermon.Audience;
import com.homiliai.core.entity.User;
import com.homiliai.core.repository.OccasionRepository;
import com.homiliai.core.repository.SermonRepository;
import java.time.LocalDate;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class SermonService {

  private static final String GOSPEL_URL_TEMPLATE = "https://niezbednik.niedziela.pl/liturgia/";

  private final SermonRepository sermonRepo;
  private final OccasionRepository occasionRepo;

  @Autowired
  public SermonService(SermonRepository sermonRepo, OccasionRepository occasionRepo) {
    this.sermonRepo = sermonRepo;
    this.occasionRepo = occasionRepo;
  }

  public Optional<Sermon> createSermon(CreateSermonDTO dto, User user) {
    String gospelText = fetchGospelText(dto.getSermonDate());
    Optional<Occasion> occasionOpt = occasionRepo.findById(dto.getOccasionId());

    if (occasionOpt.isPresent()) {
      return Optional.of(saveSermon(dto, user, gospelText, occasionOpt.get()));
    } else {
      return Optional.empty();
    }
  }


  private Sermon saveSermon(CreateSermonDTO dto, User user, String gospelText, Occasion occasion) {
    Sermon sermon = new Sermon();
    sermon.setUser(user);
    sermon.setSermonDate(dto.getSermonDate());
    sermon.setAudience(Sermon.Audience.valueOf(dto.getAudience()));
    sermon.setOccasion(occasion);
    sermon.setGospelText(gospelText);
    sermon.setContent(dto.getContent());
    sermonRepo.save(sermon);
    return sermon;
  }

  private String fetchGospelText(LocalDate date) {
    String url = GOSPEL_URL_TEMPLATE + date.toString();
    RestTemplate restTemplate = new RestTemplate();
    // Assuming the response is in plain text. Adjust the parsing accordingly.
    return restTemplate.getForObject(url, String.class);
  }

}
