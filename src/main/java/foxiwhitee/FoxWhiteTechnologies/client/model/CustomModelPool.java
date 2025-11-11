package foxiwhitee.FoxWhiteTechnologies.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class CustomModelPool extends ModelBase {
    ModelRenderer base;
    ModelRenderer side1;
    ModelRenderer side2;
    ModelRenderer side3;
    ModelRenderer side4;

    public CustomModelPool() {
        this.textureWidth = 128;
        this.textureHeight = 64;

        // BASE
        this.base = new ModelRenderer(this, 0, 0); // top at (0,0)
        this.base.addBox(0F, 0F, 0F, 16, 1, 16);
        this.base.setRotationPoint(-8F, 23F, -8F);

        // SIDE1
        this.side1 = new ModelRenderer(this, 32, 0); // front at (32,0)
        this.side1.addBox(0F, 0F, 0F, 16, 7, 1);
        this.side1.setRotationPoint(-8F, 16F, 7F);

        // SIDE2
        this.side2 = new ModelRenderer(this, 0, 24); // front at (0,24)
        this.side2.addBox(0F, 0F, 0F, 16, 7, 1);
        this.side2.setRotationPoint(-8F, 16F, -8F);

        // SIDE3
        this.side3 = new ModelRenderer(this, 0, 32); // left big face at (0,32)
        this.side3.addBox(0F, 0F, 0F, 1, 7, 14);
        this.side3.setRotationPoint(-8F, 16F, -7F);

        // SIDE4
        this.side4 = new ModelRenderer(this, 28, 32); // left big face at (28,32)
        this.side4.addBox(0F, 0F, 0F, 1, 7, 14);
        this.side4.setRotationPoint(7F, 16F, -7F);
    }


    public void render() {
        float f = 0.0625F;
        this.base.render(f);
        this.side1.render(f);
        this.side2.render(f);
        this.side3.render(f);
        this.side4.render(f);
    }
}

