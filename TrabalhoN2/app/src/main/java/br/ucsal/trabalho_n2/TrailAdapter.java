package br.ucsal.trabalho_n2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import com.google.android.gms.maps.model.LatLng;

public class TrailAdapter extends RecyclerView.Adapter<TrailAdapter.TrailViewHolder> {

    List<Trail> trails;
    private final SharedPreferences pref;
    private int selectedPosition = RecyclerView.NO_POSITION;
    private final Context context;

    public TrailAdapter(List<Trail> trails, Context context) {
        this.trails = trails;
        this.pref = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        this.context = context;
    }

    public void setSelectedPosition(int position) {
        int previousPosition = selectedPosition;
        selectedPosition = position;
        notifyItemChanged(previousPosition);
        notifyItemChanged(position);
    }

    @NonNull
    @Override
    public TrailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trail_item, parent, false);
        return new TrailViewHolder(view, this);
    }

    @Override
    public void onBindViewHolder(@NonNull TrailViewHolder holder, int position) {
        Trail trail = trails.get(position);
        double duration = trail.getDuration();
        double distance = trail.getDistance();
        double avgSpeed = trail.getAvgSpeed();
        String distanceText = formatDistance(distance);
        String avgSpeedText = formatAvgSpeed(avgSpeed);
        String durationText = formatDuration(duration);

        holder.textStartDate.setText(context.getString(R.string.start_date, trail.getStartDate()));
        holder.textDistance.setText(context.getString(R.string.distance, distanceText));
        holder.textDuration.setText(context.getString(R.string.duration, durationText));
        holder.textAvgSpeed.setText(context.getString(R.string.avg_speed, avgSpeedText));
        holder.textLatitude.setText(context.getString(R.string.latitude, formatLatitude(trail.getLatitude())));
        holder.textLongitude.setText(context.getString(R.string.longitude, formatLongitude(trail.getLongitude())));

        holder.itemView.setSelected(position == selectedPosition);

        holder.trailSelect.setOnClickListener(v -> {
            Intent intent = new Intent(context, TrailShowcase.class);
            intent.putExtra("trailId", trail.getId());
            if (trail.getCoordinates() != null) {
                intent.putParcelableArrayListExtra("trailCoordinates", new ArrayList<>(trail.getCoordinates()));
            }
            context.startActivity(intent);
        });

        holder.trailDelete.setOnClickListener(v -> {
            DatabaseHelper dbHelper = new DatabaseHelper(context);
            dbHelper.deleteTrail(trail.getId());
            trails.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, trails.size());
            dbHelper.close();
            Toast.makeText(context, "Trail deleted", Toast.LENGTH_SHORT).show();
        });
    }

    private String formatLatitude(double latitude) {
        int coordinateOption = pref.getInt("coordinates_option", 1);
        Locale locale = Locale.getDefault();
        if (Locale.getDefault().getLanguage().equals("pt")) {
            switch (coordinateOption) {
                case 1:
                    return String.format(locale, "%+.6f", latitude); // [+/-GGG.GGGGG]
                case 2:
                    int degrees = (int) latitude;
                    double minutes = (latitude - degrees) * 60;
                    return String.format(locale, "%+03d:%07.5f", degrees, minutes); // [+/-GGG:MM.MMMMM]
                case 3:
                    int deg = (int) latitude;
                    double min = (latitude - deg) * 60;
                    int minInt = (int) min;
                    double sec = (min - minInt) * 60;
                    return String.format(locale, "%+03d:%02d:%09.6f", deg, minInt, sec); // [+/-GGG:MM:SS.SSSSS]
                default:
                    return String.valueOf(latitude);
            }
        } else {
            switch (coordinateOption) {
                case 1:
                    return String.format(locale, "%+.6f", latitude); // [+/-DDD.DDDDD]
                case 2:
                    int degrees = (int) latitude;
                    double minutes = (latitude - degrees) * 60;
                    return String.format(locale, "%+03d:%07.5f", degrees, minutes); // [+/-DDD:MM.MMMMM]
                case 3:
                    int deg = (int) latitude;
                    double min = (latitude - deg) * 60;
                    int minInt = (int) min;
                    double sec = (min - minInt) * 60;
                    return String.format(locale, "%+03d:%02d:%09.6f", deg, minInt, sec); // [+/-DDD:MM:SS.SSSSS]
                default:
                    return String.valueOf(latitude);
            }
        }
    }

    private String formatLongitude(double longitude) {
        int coordinateOption = pref.getInt("coordinates_option", 1);
        Locale locale = Locale.getDefault();
        if (Locale.getDefault().getLanguage().equals("pt")) {
            // Portuguese format
            switch (coordinateOption) {
                case 1:
                    return String.format(locale, "%+.6f", longitude); // [+/-GGG.GGGGG]
                case 2:
                    int degrees = (int) longitude;
                    double minutes = (longitude - degrees) * 60;
                    return String.format(locale, "%+03d:%07.5f", degrees, minutes); // [+/-GGG:MM.MMMMM]
                case 3:
                    int deg = (int) longitude;
                    double min = (longitude - deg) * 60;
                    int minInt = (int) min;
                    double sec = (min - minInt) * 60;
                    return String.format(locale, "%+03d:%02d:%09.6f", deg, minInt, sec); // [+/-GGG:MM:SS.SSSSS]
                default:
                    return String.valueOf(longitude);
            }
        } else {
            switch (coordinateOption) {
                case 1:
                    return String.format(locale, "%+.6f", longitude); // [+/-DDD.DDDDD]
                case 2:
                    int degrees = (int) longitude;
                    double minutes = (longitude - degrees) * 60;
                    return String.format(locale, "%+03d:%07.5f", degrees, minutes); // [+/-DDD:MM.MMMMM]
                case 3:
                    int deg = (int) longitude;
                    double min = (longitude - deg) * 60;
                    int minInt = (int) min;
                    double sec = (min - minInt) * 60;
                    return String.format(locale, "%+03d:%02d:%09.6f", deg, minInt, sec); // [+/-DDD:MM:SS.SSSSS]
                default:
                    return String.valueOf(longitude);
            }
        }
    }

    private String formatDistance(double distance) {
        int speedOption = pref.getInt("speed_option", 1);
        Locale locale = Locale.getDefault();
        if (speedOption == 1) {
            if (distance >= 1000) {
                return String.format(locale, "%.2f km", distance / 1000);
            } else {
                return String.format(locale, "%.0f m", distance);
            }
        } else {
            return String.format(locale, "%.0f m", distance);
        }
    }

    private String formatAvgSpeed(double avgSpeed) {
        int speedOption = pref.getInt("speed_option", 1);
        Locale locale = Locale.getDefault();
        if (speedOption == 1) {
            return String.format(locale, "%.2f km/h", avgSpeed);
        } else {
            return String.format(locale, "%.2f m/s", avgSpeed / 3.6);
        }
    }

    private String formatDuration(double duration) {
        int hours = (int) (duration / 3600);
        int minutes = (int) ((duration % 3600) / 60);
        int seconds = (int) (duration % 60);

        if (hours > 0) {
            return String.format(Locale.getDefault(), "%d h %02d m %02d s", hours, minutes, seconds);
        } else if (minutes > 0) {
            return String.format(Locale.getDefault(), "%02d m %02d s", minutes, seconds);
        } else {
            return String.format(Locale.getDefault(), "%02d s", seconds);
        }
    }

    @Override
    public int getItemCount() {
        return trails.size();
    }

    public static class TrailViewHolder extends RecyclerView.ViewHolder {
        TextView textStartDate, textDistance, textDuration, textAvgSpeed, textLatitude, textLongitude;
        Button trailDelete, trailSelect, trailShare;

        public TrailViewHolder(@NonNull View itemView, TrailAdapter adapter) {
            super(itemView);
            textStartDate = itemView.findViewById(R.id.text_start_date);
            textDistance = itemView.findViewById(R.id.text_distance);
            textDuration = itemView.findViewById(R.id.text_duration);
            textAvgSpeed = itemView.findViewById(R.id.text_avg_speed);
            textLatitude = itemView.findViewById(R.id.text_latitude);
            textLongitude = itemView.findViewById(R.id.text_longitude);
            trailDelete = itemView.findViewById(R.id.trail_delete);
            trailSelect = itemView.findViewById(R.id.trail_select);
            trailShare = itemView.findViewById(R.id.trail_share);

            itemView.setOnClickListener(v -> {
                int adapterPosition = getAdapterPosition();
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    adapter.setSelectedPosition(adapterPosition);
                }
            });
            trailShare.setOnClickListener(v -> {
                int adapterPosition = getAdapterPosition();
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    Trail trail = adapter.trails.get(adapterPosition);
                    double latitude = trail.getLatitude();
                    double longitude = trail.getLongitude();
                    shareLocation(latitude, longitude, itemView.getContext());
                }
            });
        }
    }
    private static void shareLocation(double latitude, double longitude, Context context) {
        String location = "Latitude: " + latitude + ", Longitude: " + longitude;

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, location);

        context.startActivity(Intent.createChooser(shareIntent, "Share Location"));
    }
}
