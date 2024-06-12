package org.solar_system_game.sim;

import java.util.Random;

public class CelestialBody {
    double mass;
    public double radius;
    double distFromOrbitedBody;
    public double meanInitVelocity;
    int bodyID;
    public double[] bodyCoordinates;
    public  double[] bodyVelocity;
    double[] bodyAcceleration;
    public String Name;

    public CelestialBody(double m, double r, double d, double v,int i, String name) {
        this.Name = name;
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
        double w = rand.nextDouble()*2*Math.PI;
        this.bodyCoordinates[0] = this.distFromOrbitedBody;
        this.bodyCoordinates[1] = 0;
        this.bodyVelocity[0] = 0;
        this.bodyVelocity[1] = this.meanInitVelocity;
    }


    public void nextPosition(CelestialBody[] bodiesSet){
        double timestep = 100; //temp value, needs changing to properly scaled time step

        double[] prevStepAccel = new double[2];
        prevStepAccel[0] = this.bodyAcceleration[0];
        prevStepAccel[1] = this.bodyAcceleration[1];
        this.bodyAcceleration[0] = 0.0;
        this.bodyAcceleration[1] = 0.0;

        for (CelestialBody celestialBody : bodiesSet) {
            if (this.bodyID != celestialBody.bodyID) {
                double r_x = this.bodyCoordinates[0] - celestialBody.bodyCoordinates[0];
                double r_y = this.bodyCoordinates[1] - celestialBody.bodyCoordinates[1];

                double r_length = Math.sqrt(r_x * r_x + r_y * r_y);
                double a_total = (-1.0 * SolarSystemParameters.G * celestialBody.mass) / (r_length * r_length);
                r_x = r_x / r_length;
                r_y = r_y / r_length;
                this.bodyAcceleration[0] += r_x * a_total;
                this.bodyAcceleration[1] += r_y * a_total;
            }
        }
        this.bodyCoordinates[0] += (this.bodyVelocity[0] * timestep + 0.5 * prevStepAccel[0] * Math.pow(timestep, 2));
        this.bodyCoordinates[1] += (this.bodyVelocity[1] * timestep + 0.5 * prevStepAccel[1] * Math.pow(timestep, 2));

        this.bodyVelocity[0] += 0.5 * (this.bodyAcceleration[0] + prevStepAccel[0]) * timestep;
        this.bodyVelocity[1] += 0.5 * (this.bodyAcceleration[1] + prevStepAccel[1]) * timestep;
    }
}
