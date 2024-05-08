package org.solar_system_game.view;

/***
 Some figures, to use later during initialisation:
 length/height/wingspan: 38/18/24 m
 Launch mass: 110000 kg
 Dry mass: 78000 kg
 Payload mass: 24000 kg (we shall assume payload refers entirely to fuel mass)

 Based off https://en.wikipedia.org/wiki/Space_Shuttle_orbiter
 ***/

public class Spaceship {
    float mass;
    float fuel;
    int bodyID;
    float[] bodyCoordinates;
    float[] bodyVelocity;
    float[] bodyAcceleration;
    float[] spaceshipGeneratedAccel;

    public Spaceship(float m, float f, int i) {
        fuel = f; //initial value maybe limited for a challenge? Also in litres. Likely ~100
        mass = (float) (m + 1.1*f); //in kg, 1.1 being the density of fuel used
        //this form should make it easier to add how fuel will reduce over time
        bodyID = i;
    }

    public void nextPosition(CelestialBody[] bodiesSet){
        int timestep = 2; //temp value, needs changing to properly scaled time step

        for (int i = 0; i < bodiesSet.length; i++) {

            if (this.bodyID != bodiesSet[i].bodyID) {
                float r_x = this.bodyCoordinates[0] - bodiesSet[i].bodyCoordinates[0];
                float r_y = this.bodyCoordinates[1] - bodiesSet[i].bodyCoordinates[1];

                float r_length = (float) Math.sqrt(r_x*r_x + r_y*r_y);
                float a_total = (float) ((-1.0 * SolarSystemParameters.G * bodiesSet[i].mass)/(r_length*r_length));
                r_x = r_x/r_length;
                r_y = r_y/r_length;
                this.bodyAcceleration[0] += r_x * a_total;
                this.bodyAcceleration[1] += r_y * a_total;
            }
        }
        this.bodyAcceleration[0] += this.spaceshipGeneratedAccel[0];
        this.bodyAcceleration[1] += this.spaceshipGeneratedAccel[1];

        this.bodyVelocity[0] += this.bodyAcceleration[0] * timestep;
        this.bodyVelocity[1] += this.bodyAcceleration[1] * timestep;

        this.bodyCoordinates[0] += this.bodyVelocity[0] * timestep;
        this.bodyCoordinates[1] += this.bodyVelocity[1] * timestep;
        //currently using basic Euler integration, liable to be changed later to a diff method
    }
}

