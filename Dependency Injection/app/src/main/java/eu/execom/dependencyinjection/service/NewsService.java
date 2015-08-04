package eu.execom.dependencyinjection.service;

import org.androidannotations.annotations.rest.Get;
import org.androidannotations.annotations.rest.Rest;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.util.List;

import eu.execom.dependencyinjection.model.News;

@Rest(rootUrl ="http://beta.json-generator.com/api/json", converters = MappingJackson2HttpMessageConverter.class)
public interface NewsService {

    @Get("/get/V1KzCBd5")
    List<News> getNews();

}
