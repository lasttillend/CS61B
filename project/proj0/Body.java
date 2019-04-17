/** Create Body in space. */
public class Body {
    public double xxPos;
    public double yyPos;
    public double xxVel;
    public double yyVel;
    public double mass;
    public String imgFileName;
    static final double G = 6.67e-11;

    /** Body constructor. */
    public Body(double xP, double yP, double xV,
                double yV, double m, String img) {
        xxPos = xP;
        yyPos = yP;
        xxVel = xV;
        yyVel = yV;
        mass = m;
        imgFileName = img;
    }

    /** Body constructor. */
    public Body(Body b) {
        xxPos = b.xxPos;
        yyPos = b.yyPos;
        xxVel = b.xxVel;
        yyVel = b.yyVel;
        mass = b.mass;
        imgFileName = b.imgFileName;
    }

    /** Calculates the distance bewteen two Bodys. */
    public double calcDistance(Body other) {
        double dx = other.xxPos - this.xxPos;
        double dy = other.yyPos - this.yyPos;
        return Math.sqrt(dx * dx + dy * dy);
    }

    /** Calculates the force exerted on this body by the given body.
     */
    public double calcForceExertedBy(Body other) {
        double distance = this.calcDistance(other);
        return G * this.mass * other.mass / (distance * distance);
    }

    /** Calculates the force exerted in the X direction. */
    public double calcForceExertedByX(Body other) {
        double dx = other.xxPos - this.xxPos;
        return this.calcForceExertedBy(other) * dx / this.calcDistance(other);
    }

    /** Calculates the force exerted in the Y direction. */
    public double calcForceExertedByY(Body other) {
        double dy = other.yyPos - this.yyPos;
        return this.calcForceExertedBy(other) * dy / this.calcDistance(other);
    }

    /** Calculates the net X force exerted by all bodies in the input array upon the current Body. */
    public double calcNetForceExertedByX(Body[] bodyArray) {
        double netForceX = 0.0;
        for (Body b : bodyArray) {
            if (this.equals(b)) {
                continue;
            }
            netForceX += this.calcForceExertedByX(b);
        }
        return netForceX;
    }

    /** Calculates the net Y force exerted by all bodies in the input array upon the current Body. */
    public double calcNetForceExertedByY(Body[] bodyArray) {
        double netForceY = 0.0;
        for (Body b : bodyArray) {
            if (this.equals(b)) {
                continue;
            }
            netForceY += this.calcForceExertedByY(b);
        }
        return netForceY;
    }

    /** Updates the body's position and velocity. */
    public void update(double dt, double fX, double fY) {
        double aNetX = fX / this.mass;
        double aNetY = fY / this.mass;
        this.xxVel += aNetX * dt;
        this.yyVel += aNetY * dt;
        this.xxPos += this.xxVel * dt;
        this.yyPos += this.yyVel * dt;
    }

    /** Draw the body's image at the body's position */
    public void draw() {
        StdDraw.picture(this.xxPos, this.yyPos, "./images/" + this.imgFileName);
    }
}
