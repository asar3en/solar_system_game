package org.solar_system_game.view;

import java.util.Random;

public class CelestialBody {
    float mass;
    float radius;
    float distFromOrbitedBody;
    float meanInitVelocity;
    int bodyID;
    float[] bodyCoordinates;
    float[] bodyVelocity;
    float[] bodyAcceleration;


    public CelestialBody(float m, float r, float d, float v,int i) {
        mass = m;
        radius = r;
        bodyID = i;
        distFromOrbitedBody = d;
        meanInitVelocity = v;
    }

    public void initCoordinates(){
        Random rand = new Random();
        float w = rand.nextFloat((float) (2*Math.PI));
        this.bodyCoordinates[0] = (float) (this.distFromOrbitedBody*Math.cos(w));
        this.bodyCoordinates[1] = (float) (this.distFromOrbitedBody*Math.sin(w));
        this.bodyVelocity[0] = (float) (this.meanInitVelocity*Math.sin(w));
        this.bodyVelocity[1] = (float) (this.meanInitVelocity*Math.cos(w));
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
        this.bodyVelocity[0] += this.bodyAcceleration[0] * timestep;
        this.bodyVelocity[1] += this.bodyAcceleration[1] * timestep;

        this.bodyCoordinates[0] += this.bodyVelocity[0] * timestep;
        this.bodyCoordinates[1] += this.bodyVelocity[1] * timestep;
        //currently using basic Euler integration, liable to be changed later to a diff method
    }
}
