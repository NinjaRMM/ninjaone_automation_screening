package org.dijkstra.sample.model;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class AdditionalProperties {

@SerializedName("cast")
@Expose
private List<String> cast = null;
@SerializedName("genres")
@Expose
private List<String> genres = null;

public List<String> getCast() {
return cast;
}

public void setCast(List<String> cast) {
this.cast = cast;
}

public List<String> getGenres() {
return genres;
}

public void setGenres(List<String> genres) {
this.genres = genres;
}

}