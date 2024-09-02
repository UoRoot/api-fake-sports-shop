package es.diplock.examples.controllers.responses;

import java.util.List;

import lombok.Data;

@Data
public class ResponseBody {
    private int page;
    private List<?> results;
    private int totalPages;
    private long totalResults;
}

