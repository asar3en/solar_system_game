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
    double fuel;
    int bodyID;
    double[] bodyCoordinates;
    double[] bodyVelocity;
    double[] bodyAcceleration;
    double[] spaceshipGeneratedAccel;

    public Spaceship(double m, double f, int i) {
        fuel = f; //initial value maybe limited for a challenge? Also in litres. Likely ~100
        mass = (double) (m + 1.1*f); //in kg, 1.1 being the density of fuel used
        //this form should make it easier to add how fuel will reduce over time
        bodyID = i;
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
        this.bodyAcceleration[0] += this.spaceshipGeneratedAccel[0];
        this.bodyAcceleration[1] += this.spaceshipGeneratedAccel[1];

        this.bodyVelocity[0] += this.bodyAcceleration[0] * timestep;
        this.bodyVelocity[1] += this.bodyAcceleration[1] * timestep;

        this.bodyCoordinates[0] += this.bodyVelocity[0] * timestep;
        this.bodyCoordinates[1] += this.bodyVelocity[1] * timestep;
        //currently using basic Euler integration, liable to be changed later to a diff method
    }
}

