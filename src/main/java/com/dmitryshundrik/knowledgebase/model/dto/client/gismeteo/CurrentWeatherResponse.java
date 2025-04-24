package com.dmitryshundrik.knowledgebase.model.dto.client.gismeteo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class WeatherResponseDto {

    @JsonProperty("response")
    private Response response;

    public void setResponse(Response response) {
        this.response = response;
    }

    public static class Response {
        @JsonProperty("temperature")
        private Temperature temperature;

        @JsonProperty("humidity")
        private Humidity humidity;

        @JsonProperty("pressure")
        private Pressure pressure;

        @JsonProperty("wind")
        private Wind wind;

        @JsonProperty("cloudiness")
        private Cloudiness cloudiness;

        // Геттеры и сеттеры
        public Temperature getTemperature() {
            return temperature;
        }

        public void setTemperature(Temperature temperature) {
            this.temperature = temperature;
        }

        public Humidity getHumidity() {
            return humidity;
        }

        public void setHumidity(Humidity humidity) {
            this.humidity = humidity;
        }

        public Pressure getPressure() {
            return pressure;
        }

        public void setPressure(Pressure pressure) {
            this.pressure = pressure;
        }

        public Wind getWind() {
            return wind;
        }

        public void setWind(Wind wind) {
            this.wind = wind;
        }

        public Cloudiness getCloudiness() {
            return cloudiness;
        }

        public void setCloudiness(Cloudiness cloudiness) {
            this.cloudiness = cloudiness;
        }
    }

    public static class Temperature {
        @JsonProperty("air")
        private Air air;

        public Air getAir() {
            return air;
        }

        public void setAir(Air air) {
            this.air = air;
        }

        public static class Air {
            @JsonProperty("C")
            private double celsius;

            public double getCelsius() {
                return celsius;
            }

            public void setCelsius(double celsius) {
                this.celsius = celsius;
            }
        }
    }

    public static class Humidity {
        @JsonProperty("percent")
        private int percent;

        public int getPercent() {
            return percent;
        }

        public void setPercent(int percent) {
            this.percent = percent;
        }
    }

    public static class Pressure {
        @JsonProperty("mm_hg_atm")
        private int mmHg;

        public int getMmHg() {
            return mmHg;
        }

        public void setMmHg(int mmHg) {
            this.mmHg = mmHg;
        }
    }

    public static class Wind {
        @JsonProperty("speed")
        private Speed speed;

        public Speed getSpeed() {
            return speed;
        }

        public void setSpeed(Speed speed) {
            this.speed = speed;
        }

        public static class Speed {
            @JsonProperty("m_s")
            private int metersPerSecond;

            public int getMetersPerSecond() {
                return metersPerSecond;
            }

            public void setMetersPerSecond(int metersPerSecond) {
                this.metersPerSecond = metersPerSecond;
            }
        }
    }

    public static class Cloudiness {
        @JsonProperty("percent")
        private int percent;

        public int getPercent() {
            return percent;
        }

        public void setPercent(int percent) {
            this.percent = percent;
        }
    }
}
