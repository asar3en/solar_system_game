package org.solar_system_game.sim;

import java.util.Random;

public class CelestialBody {
    double mass;
    double radius;
    double distFromOrbitedBody;
    double meanInitVelocity;
    int bodyID;
    public double[] bodyCoordinates;
    double[] bodyVelocity;
    double[] bodyAcceleration;


    public CelestialBody(double m, double r, double d, double v,int i) {
        mass = m;
        radius = r;
        bodyID = i;
        distFromOrbitedBody = d;
        meanInitVelocity = v;
        this.bodyVelocity = new double[2];
        this.bodyCoordinates = new double[2];
        this.bodyAcceleration = new double[2];
    }

    public void initCoordinates(){
        Random rand = new Random();
        double w = rand.nextDouble((double) (2*Math.PI));
        this.bodyCoordinates[0] = (double) (this.distFromOrbitedBody*Math.cos(w));
        this.bodyCoordinates[1] = (double) (this.distFromOrbitedBody*Math.sin(w));
        this.bodyVelocity[0] = (double) (this.meanInitVelocity*Math.sin(w));
        this.bodyVelocity[1] = (double) (this.meanInitVelocity*Math.cos(w));
    }


    public void nextPosition(CelestialBody[] bodiesSet){
        int timestep = 2; //temp value, needs changing to properly scaled time step

        for (int i = 0; i < bodiesSet.length; i++) {

            if (this.bodyID != bodiesSet[i].bodyID) {
                double r_x = this.bodyCoordinates[0] - bodiesSet[i].bodyCoordinates[0];
                double r_y = this.bodyCoordinates[1] - bodiesSet[i].bodyCoordinates[1];

                double r_length = (double) Math.sqrt(r_x*r_x + r_y*r_y);
                double a_total = (double) ((-1.0 * SolarSystemParameters.G * bodiesSet[i].mass)/(r_length*r_length));
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
