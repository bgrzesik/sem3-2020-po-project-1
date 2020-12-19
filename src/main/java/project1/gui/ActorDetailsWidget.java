package project1.gui;

import glm_.vec2.Vec2;
import imgui.Cond;
import imgui.ImGui;
import project1.actors.Animal;
import project1.actors.Bush;
import project1.actors.WorldActor;
import project1.world.World;

public class ActorDetailsWidget implements Widget {
    private boolean[] windowOpenPinned = new boolean[] {true};
    private World world;
    private WorldActor pinned;

    public ActorDetailsWidget(World world, WorldActor pinned) {
        this.world = world;
        this.pinned = pinned;
    }

    @Override
    public void render(ImGui ui) {
        if (windowOpenPinned[0]) {
            String simpleName = pinned.getClass().getSimpleName();
            ui.begin("Pinned " + simpleName + " " + pinned.hashCode(), windowOpenPinned, 0);
            ui.setWindowSize(new Vec2(200, 300), Cond.Once);
            showActorDetails(ui, world, pinned, false);
            ui.end();
        }
    }

    boolean shouldRemove() {
        return !windowOpenPinned[0];
    }

    static boolean showActorDetails(ImGui ui, World world, WorldActor actor, boolean showPinned) {
        String simpleName = actor.getClass().getSimpleName();
        boolean pressed = false;
        if (ui.treeNode(actor, "%s %d", simpleName, actor.hashCode())) {
            ui.columns(2, "props", true);
            ui.text("X");
            ui.nextColumn();
            ui.text("%d", actor.getX());
            ui.nextColumn();

            ui.text("Y");
            ui.nextColumn();
            ui.text("%d", actor.getY());
            ui.nextColumn();

            ui.text("Energy");
            ui.nextColumn();
            ui.text("%d", actor.getEnergy());
            ui.nextColumn();

            ui.text("Pending removal");
            ui.nextColumn();
            ui.text("%b", actor.pendingRemoval());
            ui.nextColumn();

            if (actor instanceof Animal) {
                Animal animal = (Animal) actor;

                ui.text("Children");
                ui.nextColumn();
                ui.text("%d", animal.getChildren());
                ui.nextColumn();

                ui.text("Age");
                ui.nextColumn();
                ui.text("%d", animal.getAge());
                ui.nextColumn();

                ui.text("Genes");
                ui.nextColumn();

                int[] genes = animal.getGenes();

                String[] genesString = new String[16];
                for (int i = 0; i < 16; i++) {
                    genesString[i] = String.valueOf(genes[i]);
                }

                ui.text("%s", String.join("", genesString));
                ui.nextColumn();
                ui.nextColumn();

                for (int i = 0; i < 16; i++) {
                    genesString[i] = String.valueOf(genes[16 + i]);
                }
                ui.text("%s", String.join("", genesString));
                ui.nextColumn();
            } else if (actor instanceof Bush) {
                Bush bush = (Bush) actor;

                ui.text("Eaten");
                ui.nextColumn();
                ui.text("%b", bush.wasEaten());
                ui.nextColumn();
            }

            ui.columns(1, "", false);

            if (ui.smallButton("Delete")) {
                world.removeActor(actor);
            }

            if (showPinned) {
                ui.sameLine(0, 0);
                if (ui.smallButton("Pinned")) {
                    pressed = true;
                }
            }

            ui.treePop();
        }

        return pressed;
    }

}
