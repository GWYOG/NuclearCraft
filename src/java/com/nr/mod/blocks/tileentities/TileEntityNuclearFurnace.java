package com.nr.mod.blocks.tileentities;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;

import com.nr.mod.NuclearRelativistics;
import com.nr.mod.items.NRItems;

public class TileEntityNuclearFurnace extends TileEntity implements ISidedInventory {
	
	private String localizedName;
	
	private static final int[] slots_top = new int[]{0};
	private static final int[] slots_bottom = new int[]{1};
	private static final int[] slots_sides = new int[]{2};
	
	private ItemStack[] slots = new ItemStack[3];
	
	public static int furnaceSpeed = (int) Math.ceil((3/(double) NuclearRelativistics.nuclearFurnaceCookSpeed)*100);
	
	public int burnTime;
	
	public int currentItemBurnTime;
	
	public int cookTime;
	
	public int getSizeInventory()
	{
		return this.slots.length;
	}
	
	public String getInvName()
	{
		return this.isInvNameLocalized() ? this.localizedName : "Nuclear Furnace";
	}
	
	public boolean isInvNameLocalized()
	{
		return this.localizedName != null && this.localizedName.length() > 0;
	}
	
	public void setGuiDisplayName(String displayName)
	{
		this.localizedName = displayName;
	}

	public ItemStack getStackInSlot(int i)
	{
		return this.slots[i];
	}

	public ItemStack decrStackSize(int i, int j)
	{
		if(this.slots[i] != null)
		{
			ItemStack itemstack;
				
				if(this.slots[i].stackSize <= j)
				{
					itemstack = this.slots[i];
					
					this.slots[i] = null;
					
					return itemstack;
				}
				else
				{
					itemstack = this.slots[i].splitStack(j);
					
					if(this.slots[i].stackSize == 0)
					{
						this.slots[i] = null;
					}
					
					return itemstack;
					
				}
		}
		else
        {
            return null;
        }
	}

	public ItemStack getStackInSlotOnClosing(int i)
	{
		if(this.slots[i] != null)
		{
			ItemStack itemstack = this.slots[i];
			this.slots[i] = null;
			return itemstack;
		}
		else
        {
            return null;
        }
	}

	public void setInventorySlotContents(int i, ItemStack itemstack)
	{
		this.slots[i] = itemstack;
		
		if(itemstack != null && itemstack.stackSize > this.getInventoryStackLimit())
		{
			itemstack.stackSize = this.getInventoryStackLimit();
		}
	}

	public String getInventoryName()
	{
		return null;
	}

	public boolean hasCustomInventoryName()
	{
		return false;
	}

	public int getInventoryStackLimit()
	{
		return 64;
	}
	
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		
		NBTTagList list = nbt.getTagList("Items", 10);
		this.slots = new ItemStack[this.getSizeInventory()];
		
		for(int i = 0; i < list.tagCount(); i++)
		{
			NBTTagCompound compound = list.getCompoundTagAt(i);
			byte b = compound.getByte("Slot");
			
			if(b >= 0 && b < this.slots.length)
			{
				this.slots[b] = ItemStack.loadItemStackFromNBT(compound);
			}
		}
		
		this.burnTime = (int) nbt.getShort("BurnTime");
		this.cookTime = (int) nbt.getShort("CookTime");
		this.currentItemBurnTime = (int) nbt.getShort("CurrentBurnTime");
		
		if(nbt.hasKey("CustomName", 8))
		{
			this.localizedName = nbt.getString("CustomName");
		}
		
	}
	
	public void writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		
		nbt.setShort("BurnTime", (short) this.burnTime);
		nbt.setShort("CookTime", (short) this.cookTime);
		nbt.setShort("CurrentBurnTime", (short) this.currentItemBurnTime);
		
		NBTTagList list = new NBTTagList();
		
		for(int i = 0; i < this.slots.length; i++)
		{
			if(this.slots[i] != null)
			{
				NBTTagCompound compound = new NBTTagCompound();
				compound.setByte("Slot", (byte) i);
				this.slots[i].writeToNBT(compound);
				list.appendTag(compound);
			}
		}
		
		nbt.setTag("Items", list);
		
		if(this.isInvNameLocalized())
		{
			nbt.setString("CustomName", this.localizedName);
		}
		
	}

	public boolean isUseableByPlayer(EntityPlayer entityplayer)
	{
		return this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) != this ? false : 
		entityplayer.getDistanceSq((double)this.xCoord + 0.5D, (double)this.yCoord + 0.5D, (double)this.zCoord + 0.5D) <= 64;
	}

	public void openInventory()
	{
		
	}

	public void closeInventory()
	{
		
	}
	
	public boolean isBurning()
	{
		return this.burnTime > 0;
	}
	
	public void updateEntity()
	{
		boolean flag = burnTime > 0;
		boolean flag1 = false;
		
		if(this.burnTime > 0)
		{
			this.burnTime--;
		}
		if(!this.worldObj.isRemote)
		{
			if (this.burnTime != 0 || this.slots[1] != null && this.slots[0] != null)
            {
			if(this.burnTime == 0 && this.canSmelt())
			{
				this.currentItemBurnTime = this.burnTime = getItemBurnTime(this.slots[1]);
				
				if(this.burnTime > 0)
				{
					flag1 = true;
					
					if(this.slots[1] != null)
					{
						this.slots[1].stackSize--;
						
						if(this.slots[1].stackSize == 0)
						{
							this.slots[1] = this.slots[1].getItem().getContainerItem(this.slots[1]);
						}
						}
					}
				}
				
				if(this.isBurning() && this.canSmelt())
				{
					this.cookTime++;
					
					if(this.cookTime == TileEntityNuclearFurnace.furnaceSpeed)
					{
						this.cookTime = 0;
						this.smeltItem();
						flag1 = true;
					}
					
				}
				else
				{
					this.cookTime = 0;
				}
            }
			
			if(flag != this.burnTime > 0)
			{
				flag1 = true;
				
				BlockNuclearFurnace.updateNuclearFurnaceBlockState(this.burnTime > 0, this.worldObj, this.xCoord, this.yCoord, this.zCoord);
			}
			
		}
		
		if(flag1)
		{
			this.markDirty();
		}
	}
	
	private boolean canSmelt()
	{
		if(this.slots[0] == null)
		{
			return false;
		}
		else
		{
			ItemStack itemstack = FurnaceRecipes.smelting().getSmeltingResult(this.slots[0]);
			
			if(itemstack == null) return false;
			if(this.slots[2] == null) return true;
			if(!this.slots[2].isItemEqual(itemstack)) return false;
			
			int result = this.slots[2].stackSize + itemstack.stackSize;
			
			return result <= this.getInventoryStackLimit() && result <= this.slots[2].getMaxStackSize();
		}
	}
	
	public void smeltItem()
	{
		if(this.canSmelt())
		{
			ItemStack itemstack = FurnaceRecipes.smelting().getSmeltingResult(this.slots[0]);
			
			if(this.slots[2] == null)
			{
				this.slots[2] = itemstack.copy();
			}
			else if (this.slots[2].getItem() == itemstack.getItem())
            {
                this.slots[2].stackSize += itemstack.stackSize;
            }
			
		}
			
			this.slots[0].stackSize--;
			
			if(this.slots[0].stackSize <= 0)
			{
				this.slots[0] = null;
			}
			
	}
	
	public static int getItemBurnTime(ItemStack itemstack)
	   {
		if (itemstack == null)
        {
            return 0;
        }
        else
        {   
        	Item item = itemstack.getItem();
        	
        	if(item == new ItemStack(NRItems.material, 1, 4).getItem() && item.getDamage(itemstack) == 4)
		   {
				return (int) Math.ceil((((double) NuclearRelativistics.nuclearFurnaceCookSpeed*64)/(double) NuclearRelativistics.nuclearFurnaceCookEfficiency)*furnaceSpeed);
		   }
		   else if(item == new ItemStack(NRItems.material, 1, 5).getItem() && item.getDamage(itemstack) == 5)
		   {
				return (int) Math.ceil((((double) NuclearRelativistics.nuclearFurnaceCookSpeed*64)/(double) NuclearRelativistics.nuclearFurnaceCookEfficiency)*furnaceSpeed);
		   }
		   else if(item == new ItemStack(NRItems.material, 1, 19).getItem() && item.getDamage(itemstack) == 19)
		   {
				return (int) Math.ceil((((double) NuclearRelativistics.nuclearFurnaceCookSpeed*64)/(double) NuclearRelativistics.nuclearFurnaceCookEfficiency)*furnaceSpeed);
		   }
		   else if(item == new ItemStack(NRItems.material, 1, 20).getItem() && item.getDamage(itemstack) == 20)
		   {
				return (int) Math.ceil((((double) NuclearRelativistics.nuclearFurnaceCookSpeed*64)/(double) NuclearRelativistics.nuclearFurnaceCookEfficiency)*furnaceSpeed);
		   }
		return 0;
        }
	   }
	
	public static boolean isItemFuel(ItemStack itemstack)
	{
		return getItemBurnTime(itemstack) > 0;
	}

	public boolean isItemValidForSlot(int slot, ItemStack itemstack)
	{
		if (slot == 2) return false;
		else if (slot == 1) {
			if (isItemFuel(itemstack)) return true;
			else return false;
		}
		else if (slot == 0) {
			return true;
		}
		else return false;
	}

	public int[] getAccessibleSlotsFromSide(int var1)
	{
		return var1 == 0 ? slots_bottom : (var1 == 1 ? slots_top : slots_sides);
	}

	public boolean canInsertItem(int i, ItemStack itemstack, int j)
	{
		return this.isItemValidForSlot(i, itemstack);
	}

	public boolean canExtractItem(int slot, ItemStack stack, int side)
	{
		return slot == 2;
	}

	public int getBurnTimeRemainingScaled(int i)
	{
		if(this.currentItemBurnTime == 0)
		{
			this.currentItemBurnTime = TileEntityNuclearFurnace.furnaceSpeed;
		}
		
		return this.burnTime * i / this.currentItemBurnTime;
	}
	
	public int getCookProgressScaled(int i)
	{
		return this.cookTime * i / TileEntityNuclearFurnace.furnaceSpeed;
	}
}