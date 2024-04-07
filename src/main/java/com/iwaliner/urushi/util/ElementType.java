package com.iwaliner.urushi.util;

public  enum ElementType {
    WoodElement(0),
    FireElement(1),
    EarthElement(2),
    MetalElement(3),
    WaterElement(4),
    FAIL(-1);

        private int id;

        private ElementType(int id) {
            this.id = id;
        }
        public static ElementType getType(int id){
            switch (id){
                case 0:
                    return WoodElement;
                case 1:
                    return FireElement;
                case 2:
                    return EarthElement;
                case 3:
                    return MetalElement;
                case 4:
                    return WaterElement;
                default:
                    return FAIL;
            }

        }
        public int getID()
        {
            return this.id;
        }
    }

