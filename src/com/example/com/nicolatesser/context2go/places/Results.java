package com.example.com.nicolatesser.context2go.places;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class Results {

	@SerializedName("geometry")
	private Geometry geometry;

	@SerializedName("icon")
	private String icon;

	@SerializedName("id")
	private String id;

	@SerializedName("name")
	private String name;

	@SerializedName("reference")
	private String reference;

	@SerializedName("photo_reference")
	private String photo_reference;

	@SerializedName("photos")
	private List<Photos> photos;

	@SerializedName("rating")
	private Double rating;

	@SerializedName("types")
	private List<String> types;

	@SerializedName("vicinity")
	private String vicinity;

	@SerializedName("opening_hours")
	private OpeningHours opening_hours;

	public Geometry getGeometry() {
		return geometry;
	}

	public void setGeometry(Geometry geometry) {
		this.geometry = geometry;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}



	public List<Photos> getPhotos() {
		return photos;
	}

	public void setPhotos(List<Photos> photos) {
		this.photos = photos;
	}

	public Double getRating() {
		return rating;
	}

	public void setRating(Double rating) {
		this.rating = rating;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public List<String> getTypes() {
		return types;
	}

	public void setTypes(List<String> types) {
		this.types = types;
	}

	public String getVicinity() {
		return vicinity;
	}

	public void setVicinity(String vicinity) {
		this.vicinity = vicinity;
	}

	public OpeningHours getOpening_hours() {
		return opening_hours;
	}

	public void setOpening_hours(OpeningHours opening_hours) {
		this.opening_hours = opening_hours;
	}

	public String getPhoto_reference() {
		return photo_reference;
	}

	public void setPhoto_reference(String photo_reference) {
		this.photo_reference = photo_reference;
	}

	class Geometry {

		@SerializedName("location")
		private Location location;

		public Location getLocation() {
			return location;
		}

		public void setLocation(Location location) {
			this.location = location;
		}

	}

	class Location {
		@SerializedName("lat")
		private Double lat;

		@SerializedName("lng")
		private Double lng;

		public Double getLat() {
			return lat;
		}

		public void setLat(Double lat) {
			this.lat = lat;
		}

		public Double getLng() {
			return lng;
		}

		public void setLng(Double lng) {
			this.lng = lng;
		}

	}

	class Photos {
		@SerializedName("height")
		private int height;

		@SerializedName("width")
		private int width;

		@SerializedName("html_attributions")
		private List<String> html_attributions;

		@SerializedName("photo_reference")
		private String photo_reference;

		public int getHeight() {
			return height;
		}

		public void setHeight(int height) {
			this.height = height;
		}

		public int getWidth() {
			return width;
		}

		public void setWidth(int width) {
			this.width = width;
		}

		public List<String> getHtml_attributions() {
			return html_attributions;
		}

		public void setHtml_attributions(List<String> html_attributions) {
			this.html_attributions = html_attributions;
		}

		public String getPhoto_reference() {
			return photo_reference;
		}

		public void setPhoto_reference(String photo_reference) {
			this.photo_reference = photo_reference;
		}

	}

	class OpeningHours {

		@SerializedName("open_now")
		private boolean open_now;

		public boolean isOpen_now() {
			return open_now;
		}

		public void setOpen_now(boolean open_now) {
			this.open_now = open_now;
		}

	}

}
