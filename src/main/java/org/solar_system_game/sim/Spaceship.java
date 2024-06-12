package org.solar_system_game.sim;
/***
 Some figures, to use later during initialisation:
 length/height/wingspan: 38/18/24 m
 Launch mass: 110000 kg
 Dry mass: 78000 kg
 Payload mass: 24000 kg (we shall assume payload refers entirely to fuel mass)

 Based off https://en.wikipedia.org/wiki/Space_Shuttle_orbiter

 Maybe will be useful for later fuel calculations:
 Tsiolkovsky rocket equation: maxvchange = I_sp * g_0 * natural log (m_0/m_f)
 - where specific impulse I_sp value of our assumed fuel is 450s
 F_thrust =  I_sp * g_0 * dot_m
 - where dot_m is the propellant mass flow rate, here 400 kg/s
 ***/

public class Spaceship {
    double mass;
    public double fuel;
    int bodyID;
    public double[] bodyCoordinates = new double[2];
    double[] bodyVelocity = new double[2];;
    double[] bodyAcceleration = new double[2];;
    double[] spaceshipGeneratedAccel = new double[2];;

    public Spaceship(double m, double f, int i) {
        fuel = f;
        mass = m;
        bodyID = i;
        bodyCoordinates[0] = 0;
        bodyCoordinates[1] = 778.5*Math.pow(10, 6);
        bodyVelocity[0] = SolarSystemParameters.celestialBodyMeanOrbitalVelocities[4];
    }

    public void nextPosition(CelestialBody[] bodiesSet, Boolean isAcc, double angle){
        int timestep = 1000;
        double[] prevStepAccel = new double[2];
        prevStepAccel[0] = this.bodyAcceleration[0];
        prevStepAccel[1] = this.bodyAcceleration[1];

        this.bodyAcceleration[0] = 0.0;
        this.bodyAcceleration[1] = 0.0;

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
        this.bodyAcceleration[0] += this.spaceshipGeneratedAccel[0];
        this.bodyAcceleration[1] += this.spaceshipGeneratedAccel[1];

        this.bodyCoordinates[0] += (this.bodyVelocity[0] * timestep + 0.5 * prevStepAccel[0] * Math.pow(timestep, 2));
        this.bodyCoordinates[1] += (this.bodyVelocity[1] * timestep + 0.5 * prevStepAccel[1] * Math.pow(timestep, 2));

        this.bodyVelocity[0] += 0.5 * (this.bodyAcceleration[0] + prevStepAccel[0]) * timestep;
        this.bodyVelocity[1] += 0.5 * (this.bodyAcceleration[1] + prevStepAccel[1]) * timestep;

    }

    public void impulsiveManeuver(double angle, double ThrustDir){
        double[] RocketThrustAcc = new double [2];
        RocketThrustAcc[0] = -SolarSystemParameters.RocketThrustForce/mass*Math.sin(angle);
        RocketThrustAcc[1] = -SolarSystemParameters.RocketThrustForce/mass*Math.cos(angle)*ThrustDir;
        this.bodyVelocity[0] += RocketThrustAcc[0] * 0.5;
        this.bodyVelocity[1] += RocketThrustAcc[1] * 0.5;
        //"timestep" of roughly three seconds
        //but in the scale of the simulation it is instant
        //therefore no position change occurs, only the velocity is adjusted.
        this.mass-= 300;
        this.fuel-= 300;
    }
}

