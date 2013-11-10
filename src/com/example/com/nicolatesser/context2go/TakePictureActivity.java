package com.example.com.nicolatesser.context2go;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.example.com.nicolatesser.context2go.places.GoogleMapper;
import com.example.com.nicolatesser.context2go.places.Results;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class TakePictureActivity extends Activity {
	private static final int REQUEST_CODE = 1;
	public static final String TAG = "TakePictureActivity";
	public static final String API_KEY = "AIzaSyDF8yX9NqHxhbwPvI9DWRWxORri9RmG028";
	private Bitmap bitmap;
	private ImageView imageView;
	private Button takePhotoButton;
	private TextView dateTime;
	private TextView locationText;
	private TextView placesText;
	private Switch shoppingModeSwitch;

	private Dialog errorDialog;

	// Location Request variables
	private LocationClient mLocationClient;
	private LocationCallback mLocationCallback = new LocationCallback();
	private Location mLastLocation;
	private final int LOCATION_UPDATES_INTERVAL = 10000;
	public static boolean isAppForeground = false;
	private static final int ERROR_DIALOG_ON_CREATE_REQUEST_CODE = 4055;
	private static final int ERROR_DIALOG_ON_RESUME_REQUEST_CODE = 4056;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		checkGooglePlayServiceAvailability(ERROR_DIALOG_ON_CREATE_REQUEST_CODE);

	}

	private void init() {
		imageView = (ImageView) findViewById(R.id.result);
		dateTime = (TextView) findViewById(R.id.dateTime);
		locationText = (TextView) findViewById(R.id.location);
		takePhotoButton = (Button) findViewById(R.id.captureFront);
		placesText = (TextView) findViewById(R.id.places);
		if (mLocationClient == null) {
			mLocationClient = new LocationClient(this, mLocationCallback,
					mLocationCallback);
			Log.v(TakePictureActivity.TAG, "Location Client connect");
			if (!(mLocationClient.isConnected() || mLocationClient
					.isConnecting())) {
				mLocationClient.connect();
			}
		}

		takePhotoButton.setOnClickListener(new OnClickListener() {

			

		
			@Override
			public void onClick(View v) {
				dispatchTakePictureIntent(REQUEST_CODE);
			}
		});
	}

	private void dispatchTakePictureIntent(int actionCode) {
		Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		startActivityForResult(takePictureIntent, actionCode);
	}


	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {
		InputStream stream = null;
		if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK)
			// recyle unused bitmaps
			if (bitmap != null) {
				bitmap.recycle();
			}

		Bundle extras = intent.getExtras();
		bitmap = (Bitmap) extras.get("data");
		imageView.setImageBitmap(bitmap);

		showAdditionalInformation();
		getPlacesNearby();
		// stream = getContentResolver().openInputStream(data.getData());
		// bitmap = BitmapFactory.decodeStream(stream);
		//
		// imageView.setImageBitmap(bitmap);
		// } catch (FileNotFoundException e) {
		// e.printStackTrace();
		// } catch (IOException e) {
		// e.printStackTrace();
		// } finally {
		// if (stream != null)
		// try {
		// stream.close();
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
		// }
	}

	public void getPlacesNearby() {
		
		// TYPES:
		List<String> types = new ArrayList<String>();
		types.add("art_gallery");
		types.add("bakery");
		types.add("bicycle_store");
		types.add("book_store");
		types.add("car_dealer");
		types.add("clothing_store");
		types.add("convenience_store");
		types.add("department_store");
		types.add("electronics_store");
		types.add("florist");
		types.add("furniture_store");
		types.add("grocery_or_supermarket");
		types.add("hardware_store");
		types.add("home_goods_store");
		types.add("jewelry_store");
		types.add("liquor_store");
		types.add("shoe_store");
		types.add("shopping_mall");



		// TYPES:
		//
		// accounting
		// airport
		// amusement_park
		// aquarium
		// art_gallery
		// atm
		// bakery
		// bank
		// bar
		// beauty_salon
		// bicycle_store
		// book_store
		// bowling_alley
		// bus_station
		// cafe
		// campground
		// car_dealer
		// car_rental
		// car_repair
		// car_wash
		// casino
		// cemetery
		// church
		// city_hall
		// clothing_store
		// convenience_store
		// courthouse
		// dentist
		// department_store
		// doctor
		// electrician
		// electronics_store
		// embassy
		// establishment
		// finance
		// fire_station
		// florist
		// food
		// funeral_home
		// furniture_store
		// gas_station
		// general_contractor
		// grocery_or_supermarket
		// gym
		// hair_care
		// hardware_store
		// health
		// hindu_temple
		// home_goods_store
		// hospital
		// insurance_agency
		// jewelry_store
		// laundry
		// lawyer
		// library
		// liquor_store
		// local_government_office
		// locksmith
		// lodging
		// meal_delivery
		// meal_takeaway
		// mosque
		// movie_rental
		// movie_theater
		// moving_company
		// museum
		// night_club
		// painter
		// park
		// parking
		// pet_store
		// pharmacy
		// physiotherapist
		// place_of_worship
		// plumber
		// police
		// post_office
		// real_estate_agency
		// restaurant
		// roofing_contractor
		// rv_park
		// school
		// shoe_store
		// shopping_mall
		// spa
		// stadium
		// storage
		// store
		// subway_station
		// synagogue
		// taxi_stand
		// train_station
		// travel_agency
		// university
		// veterinary_care
		// zoo

		// https://maps.googleapis.com/maps/api/place/search/json?location=47.6899279,9.18842&radius=50&sensor=true&key=AIzaSyDF8yX9NqHxhbwPvI9DWRWxORri9RmG028

		String url = "https://maps.googleapis.com/maps/api/place/search/json?location="
				+ mLastLocation.getLatitude()
				+ ","
				+ mLastLocation.getLongitude()
				+ "&radius=50&sensor=true&key="
				+ API_KEY
 + "&rankBy=distance";

		shoppingModeSwitch = (Switch) findViewById(R.id.shoppingModeSwitch);

		boolean shoppingMode = shoppingModeSwitch.isChecked();
		;
		if (shoppingMode) {
			url += "&types="
					+ URLEncoder.encode(strJoin(types.toArray(new String[0]),
							"|"));
		}


		Log.i(TAG, "Calling places" + url);
		
		

		DownloadPlacesTask placesTask = new DownloadPlacesTask();
		placesTask.execute(url);

		// TODO make a request to the place API

		// HttpRequestFactory httpRequestFactory = createRequestFactory(null);
		// HttpRequest request = httpRequestFactory
		// .buildGetRequest(new GenericUrl(
		// "https://maps.googleapis.com/maps/api/place/search/json?"));
		// request.getUrl().put("key", API_KEY);
		// request.getUrl().put("location", _latitude + "," + _longitude);
		// request.getUrl().put("name", "Pacific Beach");
		// request.getUrl().put("radius", 30); // in meters
		// request.getUrl().put("sensor", "false");

	}

	public String strJoin(String[] aArr, String sSep) {
		StringBuilder sbStr = new StringBuilder();
		for (int i = 0, il = aArr.length; i < il; i++) {
			if (i > 0)
				sbStr.append(sSep);
			sbStr.append(aArr[i]);
		}
		return sbStr.toString();
	}
	//
	// public static HttpRequestFactory createRequestFactory(final HttpTransport
	// transport) {
	// return transport.createRequestFactory(new HttpRequestInitializer() {
	// public void initialize(HttpRequest request) {
	// GoogleHeaders headers = new GoogleHeaders();
	// headers.setApplicationName("MyFirstLocationApp");
	// request.setHeaders(headers);
	// JsonHttpParser parser = new JsonHttpParser(new JacksonFactory());
	// request.addParser(parser);
	// }
	// });
	// }

	private void showAdditionalInformation() {

		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"dd.mm.yyyy hh:mm:ss");
		String dateText = dateFormat.format(new Date());
		dateTime.setText("Datetime: " + dateText);

	}

	private class LocationCallback implements ConnectionCallbacks,
			OnConnectionFailedListener, LocationListener {

		@Override
		public void onConnected(Bundle connectionHint) {
			Log.v(TakePictureActivity.TAG, "Location Client connected");

			// Display last location
			Location location = mLocationClient.getLastLocation();
			if (location != null) {
				handleLocation(location);
			}

			// Request for location updates
			LocationRequest request = LocationRequest.create();
			request.setInterval(LOCATION_UPDATES_INTERVAL);
			request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
			mLocationClient.requestLocationUpdates(request, mLocationCallback);

			// Setup map to allow adding Geo Fences
		}

		@Override
		public void onDisconnected() {
			Log.v(TakePictureActivity.TAG,
					"Location Client disconnected by the system");
		}

		@Override
		public void onConnectionFailed(ConnectionResult result) {
			Log.v(TakePictureActivity.TAG, "Location Client connection failed");
		}

		@Override
		public void onLocationChanged(Location location) {
			if (location == null) {
				Log.v(TakePictureActivity.TAG,
						"onLocationChanged: location == null");
				return;
			}
			// Add a marker iff location has changed.
			if (mLastLocation != null
					&& mLastLocation.getLatitude() == location.getLatitude()
					&& mLastLocation.getLongitude() == location.getLongitude()) {
				return;
			}

			handleLocation(location);
		}

		private void handleLocation(Location location) {
			// Update the mLocationStatus with the lat/lng of the location
			Log.v(TakePictureActivity.TAG,
					"LocationChanged == @" + location.getLatitude() + ","
							+ location.getLongitude());
			locationText.setText("Location changed @"
					+ location.getLatitude() + "," + location.getLongitude());

			// Add a marker of that location to the map
			LatLng latlongzoom = new LatLng(location.getLatitude(),
					location.getLongitude());
			String snippet = location.getLatitude() + ","
					+ location.getLongitude();
			// Marker marker = mMap.addMarker(new MarkerOptions()
			// .position(latlongzoom));
			// marker.setSnippet(snippet);
			// marker.setTitle(snippet);

			// Center the map to the first marker
			if (mLastLocation == null) {
				// mMap.moveCamera(CameraUpdateFactory
				// .newCameraPosition(CameraPosition.fromLatLngZoom(
				// new LatLng(location.getLatitude(), location
				// .getLongitude()), (float) 16.0)));
			}
			mLastLocation = location;
		}

	};

	@Override
	public void onPause() {
		super.onPause();

		// Indicate the application is in background
		isAppForeground = false;

		if (mLocationClient.isConnected()) {
			mLocationClient.removeLocationUpdates(mLocationCallback);
			mLocationClient.disconnect();
		}
	}

	@Override
	public void onResume() {
		super.onResume();

		// Indicate the application is in foreground
		isAppForeground = true;

		checkGooglePlayServiceAvailability(ERROR_DIALOG_ON_RESUME_REQUEST_CODE);

		restartLocationClient();
	}

	private void restartLocationClient() {
		if (!(mLocationClient.isConnected() || mLocationClient.isConnecting())) {
			mLocationClient.connect(); // Somehow it becomes connected here
			return;
		}

		if (mLocationClient.isConnecting()) {
			return;
		}

		LocationRequest request = LocationRequest.create();
		request.setInterval(LOCATION_UPDATES_INTERVAL);
		request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
		mLocationClient.requestLocationUpdates(request, mLocationCallback);
	}

	private void checkGooglePlayServiceAvailability(int requestCode) {
		// Query for the status of Google Play services on the device
		int statusCode = GooglePlayServicesUtil
				.isGooglePlayServicesAvailable(getBaseContext());

		if (statusCode == ConnectionResult.SUCCESS) {
			init();
		} else {
			if (GooglePlayServicesUtil.isUserRecoverableError(statusCode)) {
				errorDialog = GooglePlayServicesUtil.getErrorDialog(statusCode,
						this, requestCode);
				errorDialog.show();
			} else {
				// Handle unrecoverable error
			}
		}
	}

	private class DownloadPlacesTask extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... urls) {
			String response = "";
			for (String url : urls) {
				DefaultHttpClient client = new DefaultHttpClient();
				HttpGet httpGet = new HttpGet(url);
				try {
					HttpResponse execute = client.execute(httpGet);
					InputStream content = execute.getEntity().getContent();

					BufferedReader buffer = new BufferedReader(
							new InputStreamReader(content));
					String s = "";
					while ((s = buffer.readLine()) != null) {
						response += s;
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return response;
		}

		@Override
		protected void onPostExecute(String result) {

			Gson gson = new GsonBuilder().serializeNulls().create();
			// String json = gson.toJson(result);
			Log.d(TAG, result);
			GoogleMapper mapper = gson.fromJson(result, GoogleMapper.class);

			String placesString = "Found " + mapper.getResults().size()
					+ "possible Places results:\n";

			for (Results placeResult : mapper.getResults()) {
				placesString += placeResult.getName() + "\n";
			}
			placesText.setText(placesString);

		}
	}

}