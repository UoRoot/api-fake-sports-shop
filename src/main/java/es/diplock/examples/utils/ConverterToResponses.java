package es.diplock.examples.utils;

import org.springframework.data.domain.Page;

import es.diplock.examples.controllers.responses.ResponseBody;

/**
 * Utility class that provides methods for converting paginated data 
 * into a standardized response format.
 * <p>
 * This class contains static methods that can be used to convert Spring Data's
 * {@link org.springframework.data.domain.Page} objects into {@link ResponseBody}
 * objects, which are used to encapsulate paginated results in a consistent manner.
 * </p>
 */
public class ConverterToResponses {
    
    /**
     * Converts a {@link org.springframework.data.domain.Page} object into a 
     * {@link ResponseBody} object.
     * <p>
     * This method takes a paginated result from a Spring Data repository and
     * converts it into a {@link ResponseBody} object, which contains details
     * about the current page, the total number of pages, the total number of 
     * results, and the list of results on the current page.
     * </p>
     *
     * @param response the {@link org.springframework.data.domain.Page} object 
     *                 containing the paginated data to convert.
     * @return a {@link ResponseBody} object containing the paginated data 
     *         and metadata.
     */
    public static ResponseBody pageToResponseBody(Page<?> response) {
        ResponseBody responseBody = new ResponseBody();
        responseBody.setPage(response.getNumber() + 1);
        responseBody.setResults(response.getContent());
        responseBody.setTotalPages(response.getTotalPages());
        responseBody.setTotalResults(response.getTotalElements());
        return responseBody;
    }
}

