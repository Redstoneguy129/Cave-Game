package me.cameronwhyte.cavegame.camera;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class FirstPersonCamera {
    private Vector3f position;
    private Vector3f lookDirection;
    private Vector3f orientation;

    private float yaw = 0;
    private float pitch = 0;

    private float lastMouseX = 0;
    private float lastMouseY = 0;

    public FirstPersonCamera(float x, float y, float z) {
        this.position = new Vector3f(x, y, z);
        this.lookDirection = new Vector3f(0, 0, -1);
        this.orientation = new Vector3f(0, 1, 0);
    }

    public void translate(float x, float y, float z) {
        this.position.add(x, y, z);
    }

    public void setLookDirection(float x, float y) {
        this.lastMouseX = x;
        this.lastMouseY = y;
        this.yaw = x - lastMouseX;
        this.pitch = y - lastMouseY;
        this.lookDirection.rotateY((float) Math.toRadians(yaw), this.lookDirection);
        this.lookDirection.rotateX((float) Math.toRadians(pitch), this.lookDirection);
    }

    public Matrix4f getMatrix() {
        return new Matrix4f().lookAt(position, lookDirection, orientation);
    }
}
