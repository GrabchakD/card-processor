package org.pb.controller;

import lombok.RequiredArgsConstructor;
import org.pb.controller.dto.operator.OperatorDto;
import org.pb.model.statistic.Statistic;
import org.pb.service.StatisticService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/statistic")
@RequiredArgsConstructor
public class StatisticController {
    private final StatisticService statisticService;
    private static final Logger logger = LoggerFactory.getLogger(StatisticController.class);

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<Statistic> getStatistic() {
        logger.info("Attempt to get statistic");
        return ResponseEntity.ok(statisticService.getStatistic());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<byte[]> generateCSVOfOperators(@RequestBody List<OperatorDto> operators) {
        logger.info("Attempt to generate operators CSV");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", "operators.csv");
        return ResponseEntity.ok(statisticService.generateSCVWithOperators(operators));
    }
}
