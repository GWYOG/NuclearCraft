package com.nr.mod.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

import com.nr.mod.blocks.tileentities.TileEntityFusionReactor;
import com.nr.mod.container.ContainerFusionReactor;

public class GuiFusionReactor extends GuiContainer
{
    public static final ResourceLocation bground = new ResourceLocation("nr:textures/gui/fusionReactor.png");
    
    public TileEntityFusionReactor entity;

    public GuiFusionReactor(InventoryPlayer inventoryPlayer, TileEntityFusionReactor entity)
    {
        super(new ContainerFusionReactor(inventoryPlayer, entity));
        this.entity = entity;
        this.xSize = 244;
        this.ySize = 256;
    }
    
    public String f1() {
    	if (this.entity.HLevel > 0) {return "Hydrogen";}
    	else if (this.entity.DLevel > 0) {return "Deuterium";}
    	else if (this.entity.TLevel > 0) {return "Tritium";}
    	else if (this.entity.HeLevel > 0) {return "Helium-3";}
    	else if (this.entity.BLevel > 0) {return "Boron-11";}
    	else if (this.entity.Li6Level > 0) {return "Lithium-6";}
    	else if (this.entity.Li7Level > 0) {return "Lithium-7";}
    	else {return "Fuel";}
    }
    
    public String f2() {
    	if (this.entity.HLevel2 > 0) {return "Hydrogen";}
    	else if (this.entity.DLevel2 > 0) {return "Deuterium";}
    	else if (this.entity.TLevel2 > 0) {return "Tritium";}
    	else if (this.entity.HeLevel2 > 0) {return "Helium-3";}
    	else if (this.entity.BLevel2 > 0) {return "Boron-11";}
    	else if (this.entity.Li6Level2 > 0) {return "Lithium-6";}
    	else if (this.entity.Li7Level2 > 0) {return "Lithium-7";}
    	else {return "Fuel";}
    }
    
    public int level1() {
    	if (this.entity.HLevel > 0) {return this.entity.HLevel;}
    	else if (this.entity.DLevel > 0) {return this.entity.DLevel;}
    	else if (this.entity.TLevel > 0) {return this.entity.TLevel;}
    	else if (this.entity.HeLevel > 0) {return this.entity.HeLevel;}
    	else if (this.entity.BLevel > 0) {return this.entity.BLevel;}
    	else if (this.entity.Li6Level > 0) {return this.entity.Li6Level;}
    	else if (this.entity.Li7Level > 0) {return this.entity.Li7Level;}
    	else {return 0;}
    }
    
    public int level2() {
    	if (this.entity.HLevel2 > 0) {return this.entity.HLevel2;}
    	else if (this.entity.DLevel2 > 0) {return this.entity.DLevel2;}
    	else if (this.entity.TLevel2 > 0) {return this.entity.TLevel2;}
    	else if (this.entity.HeLevel2 > 0) {return this.entity.HeLevel2;}
    	else if (this.entity.BLevel2 > 0) {return this.entity.BLevel2;}
    	else if (this.entity.Li6Level2 > 0) {return this.entity.Li6Level2;}
    	else if (this.entity.Li7Level2 > 0) {return this.entity.Li7Level2;}
    	else {return 0;}
    }

    public void drawGuiContainerForegroundLayer(int par1, int par2)
    {
        String name1 = StatCollector.translateToLocal("tile.fusionReactor.name");
        this.fontRendererObj.drawString(name1, 11, 11, -1);
        String power = StatCollector.translateToLocal("gui.energyStored") + ": " + this.entity.energy + " RF";
        this.fontRendererObj.drawString(power, 11, 21, -1);
        String fuel1 = f2() + " " + StatCollector.translateToLocal("gui.level1") + ": " + Math.round(Math.floor(level2() / 672)) + " l";
        this.fontRendererObj.drawString(fuel1, 11, 31, -1);
        String fuel2 = f1() + " " + StatCollector.translateToLocal("gui.level2") + ": " + Math.round(Math.floor(level1() / 672)) + " l";
        this.fontRendererObj.drawString(fuel2, 11, 41, -1);
        String egen = ((this.entity.HLevel + this.entity.DLevel + this.entity.TLevel + this.entity.HeLevel + this.entity.BLevel + this.entity.Li6Level + this.entity.Li7Level == 0 || this.entity.HLevel2 + this.entity.DLevel2 + this.entity.TLevel2 + this.entity.HeLevel2 + this.entity.BLevel2 + this.entity.Li6Level2 + this.entity.Li7Level2 == 0) ? 0 : this.entity.EShown) + " RF/t";
        this.fontRendererObj.drawString(egen, 11, 51, -1);
        String size = StatCollector.translateToLocal("gui.reactorSize") + ": " + entity.size;
        this.fontRendererObj.drawString(size, 11, 61, -1);
        String efficiency = StatCollector.translateToLocal("gui.efficiency") + ": " + Math.round(entity.efficiency) + "%";
        this.fontRendererObj.drawString(efficiency, 11, 71, -1);
        String heat = StatCollector.translateToLocal("gui.temp") + ": " + Math.round(entity.heat) + " MK";
        this.fontRendererObj.drawString(heat, 11, 81, -1);
        //String heatVar = StatCollector.translateToLocal("gui.heatVar") + ": " + entity.heatVar;
        //this.fontRendererObj.drawString(heatVar, 11, 91, -1);
        String input = StatCollector.translateToLocal("gui.input");
        this.fontRendererObj.drawString(input, 49, 158, -1);
        //String cells = StatCollector.translateToLocal("gui.cells");
        //this.fontRendererObj.drawString(cells, 166-this.fontRendererObj.getStringWidth(cells), 158, -1);
        String out = StatCollector.translateToLocal("gui.output");
        this.fontRendererObj.drawString(out, 172+32-this.fontRendererObj.getStringWidth(out)/2, 236, -1);
    }

    protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        Minecraft.getMinecraft().getTextureManager().bindTexture(bground);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
        int k = this.entity.energy * 162 / 10000000;
        for (int texi = 0; texi < 5; ++texi) {
        	this.drawTexturedModalRect(this.guiLeft + 213 + texi, this.guiTop + 8 + 162 - k, 244, 3 + 162 - k, 1, k);
        }
        int heat1 = (int) (this.entity.heat * 162 / 20000);
        for (int texi2 = 0; texi2 < 5; ++texi2) {
        	this.drawTexturedModalRect(this.guiLeft + 203 + texi2, this.guiTop + 8 + 162 - heat1, 245, 3 + 162 - heat1, 1, heat1);
        }
        int ef = (int) (this.entity.efficiency * 162 / 100);
        for (int texi3 = 0; texi3 < 5; ++texi3) {
        	this.drawTexturedModalRect(this.guiLeft + 193 + texi3, this.guiTop + 8 + 162 - ef, 246, 3 + 162 - ef, 1, ef);
        }
        int k2 = level1() * 162 / 12096000;
        this.drawTexturedModalRect(this.guiLeft + 231, this.guiTop + 8 + 162 - k2, 249, 3 + 162 - k2, 5, k2);
        int k3 = level2() * 162 / 12096000;
        this.drawTexturedModalRect(this.guiLeft + 222, this.guiTop + 8 + 162 - k3, 249, 3 + 162 - k3, 5, k3);
        
        int HOut = (int) Math.round(this.entity.HOut * 14 / 336000);
        if (this.entity.HOut <= 336000) drawTexturedModalRect(guiLeft + 197, guiTop + 199, 241, 0, HOut, 1); else drawTexturedModalRect(guiLeft + 197, guiTop + 199, 241, 0, 14, 1);
        int DOut = (int) Math.round(this.entity.DOut * 14 / 336000);
        if (this.entity.DOut <= 336000) drawTexturedModalRect(guiLeft + 221, guiTop + 199, 241, 0, DOut, 1); else drawTexturedModalRect(guiLeft + 221, guiTop + 199, 241, 0, 14, 1);
        int TOut = (int) Math.round(this.entity.TOut * 14 / 336000);
        if (this.entity.TOut <= 336000) drawTexturedModalRect(guiLeft + 173, guiTop + 223, 241, 0, TOut, 1); else drawTexturedModalRect(guiLeft + 173, guiTop + 223, 241, 0, 14, 1);
        int HE3Out = (int) Math.round(this.entity.HE3Out * 14 / 336000);
        if (this.entity.HE3Out <= 336000) drawTexturedModalRect(guiLeft + 197, guiTop + 223, 241, 0, HE3Out, 1); else drawTexturedModalRect(guiLeft + 197, guiTop + 223, 241, 0, 14, 1);
        int HE4Out = (int) Math.round(this.entity.HE4Out * 14 / 336000);
        if (this.entity.HE4Out <= 336000) drawTexturedModalRect(guiLeft + 221, guiTop + 223, 241, 0, HE4Out, 1); else drawTexturedModalRect(guiLeft + 221, guiTop + 223, 241, 0, 14, 1);
        int nOut = (int) Math.round(this.entity.nOut * 14 / 336000);
        if (this.entity.nOut <= 336000) drawTexturedModalRect(guiLeft + 173, guiTop + 199, 241, 0, nOut, 1); else drawTexturedModalRect(guiLeft + 173, guiTop + 199, 241, 0, 14, 1);
    }
}
