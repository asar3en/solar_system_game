package org.solar_system_game.view;

import java.util.Random;

public class CelestialBody {
    float mass;
    float radius;
    float distFromOrbitedBody;
    float meanInitVelocity;
    float[] bodyCoordinates;
    float[] bodyVelocity;
    float[] bodyAcceleration;


    public CelestialBody(float m, float r, float d, float v) {
        mass = m;
        radius = r;
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


    int nextposition(){
        return 0;
    }


}
