package de.bahr;

/**
 * Created by michaelbahr on 30/12/2016.
 */
public class CapModules {

    public static boolean isCapModule(String typeName) {
        return list.contains(typeName);
    }

    private static String list = "Shadow\n" +
            "CONCORD Capital Armor Repairer\n" +
            "CONCORD Capital Remote Armor Repairer\n" +
            "CONCORD Capital Shield Booster\n" +
            "CONCORD Capital Remote Shield Booster\n" +
            "CONCORD Ion Siege Blaster\n" +
            "CONCORD Ion Siege Blaster Blueprint\n" +
            "CONCORD Dual 1000mm Railgun\n" +
            "CONCORD Dual 1000mm Railgun Blueprint\n" +
            "CONCORD Dual Giga Pulse Laser\n" +
            "CONCORD Dual Giga Pulse Laser Blueprint\n" +
            "CONCORD Dual Giga Beam Laser\n" +
            "CONCORD Dual Giga Beam Laser Blueprint\n" +
            "CONCORD Quad 3500mm Siege Artillery\n" +
            "CONCORD Hexa 2500mm Repeating Cannon\n" +
            "CONCORD Capital Remote Capacitor Transmitter\n" +
            "Capital Remote Shield Booster I\n" +
            "Capital Remote Shield Booster II\n" +
            "Capital Tractor Beam II\n" +
            "Triage Module II\n" +
            "Triage Module II Blueprint\n" +
            "X-Large Shield Booster I\n" +
            "X-Large Shield Booster II\n" +
            "X-Large Neutron Saturation Injector I\n" +
            "X-Large Clarity Ward Booster I\n" +
            "X-Large Converse Deflection Catalyzer\n" +
            "X-Large C5-L Emergency Shield Overload I\n" +
            "Capital Remote Capacitor Transmitter I\n" +
            "Capital Remote Capacitor Transmitter II\n" +
            "Dread Guristas X-Large Shield Booster\n" +
            "Domination X-Large Shield Booster\n" +
            "Hakim's Modified X-Large Shield Booster\n" +
            "Tobias' Modified X-Large Shield Booster\n" +
            "Kaikka's Modified X-Large Shield Booster\n" +
            "Thon's Modified X-Large Shield Booster\n" +
            "Vepas' Modified X-Large Shield Booster\n" +
            "Estamel's Modified X-Large Shield Booster\n" +
            "Caldari Navy X-Large Shield Booster\n" +
            "Republic Fleet X-Large Shield Booster\n" +
            "Antimatter Charge XL\n" +
            "Antimatter Charge XL Blueprint\n" +
            "Iridium Charge XL\n" +
            "Iridium Charge XL Blueprint\n" +
            "Iron Charge XL\n" +
            "Iron Charge XL Blueprint\n" +
            "Lead Charge XL\n" +
            "Lead Charge XL Blueprint\n" +
            "Plutonium Charge XL\n" +
            "Plutonium Charge XL Blueprint\n" +
            "Thorium Charge XL\n" +
            "Thorium Charge XL Blueprint\n" +
            "Tungsten Charge XL\n" +
            "Tungsten Charge XL Blueprint\n" +
            "Uranium Charge XL\n" +
            "Uranium Charge XL Blueprint\n" +
            "Carbonized Lead XL\n" +
            "Carbonized Lead XL Blueprint\n" +
            "Depleted Uranium XL\n" +
            "Depleted Uranium XL Blueprint\n" +
            "EMP XL\n" +
            "EMP XL Blueprint\n" +
            "Fusion XL\n" +
            "Fusion XL Blueprint\n" +
            "Nuclear XL\n" +
            "Nuclear XL Blueprint\n" +
            "Phased Plasma XL\n" +
            "Phased Plasma XL Blueprint\n" +
            "Proton XL\n" +
            "Proton XL Blueprint\n" +
            "Titanium Sabot XL\n" +
            "Titanium Sabot XL Blueprint\n" +
            "Gamma XL\n" +
            "Gamma XL Blueprint\n" +
            "Infrared XL\n" +
            "Infrared XL Blueprint\n" +
            "Microwave XL\n" +
            "Microwave XL Blueprint\n" +
            "Multifrequency XL\n" +
            "Multifrequency XL Blueprint\n" +
            "Radio XL\n" +
            "Radio XL Blueprint\n" +
            "Standard XL\n" +
            "Standard XL Blueprint\n" +
            "Ultraviolet XL\n" +
            "Ultraviolet XL Blueprint\n" +
            "Xray XL\n" +
            "Xray XL Blueprint\n" +
            "Gist C-Type X-Large Shield Booster\n" +
            "Gist B-Type X-Large Shield Booster\n" +
            "Gist A-Type X-Large Shield Booster\n" +
            "Gist X-Type X-Large Shield Booster\n" +
            "Pith C-Type X-Large Shield Booster\n" +
            "Pith B-Type X-Large Shield Booster\n" +
            "Pith A-Type X-Large Shield Booster\n" +
            "Pith X-Type X-Large Shield Booster\n" +
            "Sanshas Radio XL\n" +
            "Sanshas Microwave XL\n" +
            "Sanshas Infrared XL\n" +
            "Sanshas Standard XL\n" +
            "Dual Giga Pulse Laser I\n" +
            "Dual Giga Pulse Laser I Blueprint\n" +
            "Dual Giga Beam Laser I\n" +
            "Dual Giga Beam Laser I Blueprint\n" +
            "Dual 1000mm Railgun I\n" +
            "Dual 1000mm Railgun I Blueprint\n" +
            "Ion Siege Blaster I\n" +
            "Ion Siege Blaster I Blueprint\n" +
            "Hexa 2500mm Repeating Cannon I\n" +
            "Hexa 2500mm Repeating Cannon I Blueprint\n" +
            "Quad 3500mm Siege Artillery I\n" +
            "Quad 3500mm Siege Artillery I Blueprint\n" +
            "X-Large 'Locomotive' Shield Booster\n" +
            "Capital Armor Repairer I\n" +
            "Capital Shield Booster I\n" +
            "Arch Angel Carbonized Lead XL\n" +
            "Arch Angel Depleted Uranium XL\n" +
            "Arch Angel EMP XL\n" +
            "Arch Angel Fusion XL\n" +
            "Arch Angel Nuclear XL\n" +
            "Arch Angel Phased Plasma XL\n" +
            "Arch Angel Proton XL\n" +
            "Arch Angel Titanium Sabot XL\n" +
            "Domination Carbonized Lead XL\n" +
            "Domination Depleted Uranium XL\n" +
            "Domination EMP XL\n" +
            "Domination Fusion XL\n" +
            "Domination Nuclear XL\n" +
            "Domination Phased Plasma XL\n" +
            "Domination Proton XL\n" +
            "Domination Titanium Sabot XL\n" +
            "Sanshas Ultraviolet XL\n" +
            "Sanshas Xray XL\n" +
            "Sanshas Gamma XL\n" +
            "Sanshas Multifrequency XL\n" +
            "True Sanshas Radio XL\n" +
            "True Sanshas Microwave XL\n" +
            "True Sanshas Infrared XL\n" +
            "True Sanshas Standard XL\n" +
            "True Sanshas Ultraviolet XL\n" +
            "True Sanshas Xray XL\n" +
            "True Sanshas Gamma XL\n" +
            "True Sanshas Multifrequency XL\n" +
            "Shadow Antimatter Charge XL\n" +
            "Shadow Iridium Charge XL\n" +
            "Shadow Iron Charge XL\n" +
            "Shadow Lead Charge XL\n" +
            "Shadow Plutonium Charge XL\n" +
            "Shadow Thorium Charge XL\n" +
            "Shadow Tungsten Charge XL\n" +
            "Shadow Uranium Charge XL\n" +
            "Guardian Antimatter Charge XL\n" +
            "Guardian Iridium Charge XL\n" +
            "Guardian Iron Charge XL\n" +
            "Guardian Lead Charge XL\n" +
            "Guardian Plutonium Charge XL\n" +
            "Guardian Thorium Charge XL\n" +
            "Guardian Tungsten Charge XL\n" +
            "Guardian Uranium Charge XL\n" +
            "Blood Radio XL\n" +
            "Blood Microwave XL\n" +
            "Blood Infrared XL\n" +
            "Blood Standard XL\n" +
            "Blood Ultraviolet XL\n" +
            "Blood Xray XL\n" +
            "Blood Gamma XL\n" +
            "Blood Multifrequency XL\n" +
            "Dark Blood Radio XL\n" +
            "Dark Blood Microwave XL\n" +
            "Dark Blood Infrared XL\n" +
            "Dark Blood Standard XL\n" +
            "Dark Blood Ultraviolet XL\n" +
            "Dark Blood Xray XL\n" +
            "Dark Blood Gamma XL\n" +
            "Dark Blood Multifrequency XL\n" +
            "Guristas Antimatter Charge XL\n" +
            "Guristas Iridium Charge XL\n" +
            "Guristas Iron Charge XL\n" +
            "Guristas Lead Charge XL\n" +
            "Guristas Plutonium Charge XL\n" +
            "Guristas Thorium Charge XL\n" +
            "Guristas Tungsten Charge XL\n" +
            "Guristas Uranium Charge XL\n" +
            "Dread Guristas Antimatter Charge XL\n" +
            "Dread Guristas Iridium Charge XL\n" +
            "Dread Guristas Iron Charge XL\n" +
            "Dread Guristas Lead Charge XL\n" +
            "Dread Guristas Plutonium Charge XL\n" +
            "Dread Guristas Thorium Charge XL\n" +
            "Dread Guristas Tungsten Charge XL\n" +
            "Dread Guristas Uranium Charge XL\n" +
            "Republic Fleet EMP XL\n" +
            "Republic Fleet Fusion XL\n" +
            "Republic Fleet Nuclear XL\n" +
            "Republic Fleet Phased Plasma XL\n" +
            "Republic Fleet Proton XL\n" +
            "Republic Fleet Titanium Sabot XL\n" +
            "'Capitalist' Magnetic Field Stabilizer I\n" +
            "Templar I\n" +
            "Dragonfly I\n" +
            "Firbolg I\n" +
            "Einherji I\n" +
            "Fighter Support Unit I\n" +
            "Capital Remote Armor Repairer I\n" +
            "Supercapital Ship Assembly Array\n" +
            "Capital Tractor Beam I\n" +
            "Capital Shipyard\n" +
            "Capital Ship Assembly Array\n" +
            "Starbase Capital Ship Maintenance Array\n" +
            "Starbase Capital Shipyard\n" +
            "Capital Auxiliary Nano Pump I\n" +
            "Capital Nanobot Accelerator II\n" +
            "ECM Jammer Burst Projector\n" +
            "Capital Remote Hull Repairer I\n" +
            "Triage Module I\n" +
            "Triage Module I Blueprint\n" +
            "Republic Fleet Carbonized Lead XL\n" +
            "Republic Fleet Depleted Uranium XL\n" +
            "Capital Trimark Armor Pump I\n" +
            "Capital Anti-EM Pump I\n" +
            "Capital Anti-EM Pump II\n" +
            "Capital Anti-Explosive Pump I\n" +
            "Capital Anti-Explosive Pump II\n" +
            "Capital Anti-Kinetic Pump I\n" +
            "Capital Anti-Kinetic Pump II\n" +
            "Capital Anti-Thermal Pump I\n" +
            "Capital Anti-Thermal Pump II\n" +
            "Capital Auxiliary Nano Pump II\n" +
            "Capital Trimark Armor Pump II\n" +
            "Capital Nanobot Accelerator I\n" +
            "Capital Remote Repair Augmentor I\n" +
            "Capital Remote Repair Augmentor II\n" +
            "Capital Salvage Tackle I\n" +
            "Capital Salvage Tackle II\n" +
            "Capital Auxiliary Thrusters I\n" +
            "Capital Auxiliary Thrusters II\n" +
            "Capital Cargohold Optimization I\n" +
            "Capital Cargohold Optimization II\n" +
            "Capital Dynamic Fuel Valve I\n" +
            "Capital Dynamic Fuel Valve II\n" +
            "Capital Engine Thermal Shielding I\n" +
            "Capital Engine Thermal Shielding II\n" +
            "Capital Low Friction Nozzle Joints I\n" +
            "Capital Hyperspatial Velocity Optimizer I\n" +
            "Capital Hyperspatial Velocity Optimizer II\n" +
            "Capital Low Friction Nozzle Joints II\n" +
            "Capital Polycarbon Engine Housing I\n" +
            "Capital Polycarbon Engine Housing II\n" +
            "Capital Warp Core Optimizer I\n" +
            "Capital Warp Core Optimizer II\n" +
            "Capital Emission Scope Sharpener I\n" +
            "Capital Emission Scope Sharpener II\n" +
            "Capital Gravity Capacitor Upgrade I\n" +
            "Capital Gravity Capacitor Upgrade II\n" +
            "Capital Liquid Cooled Electronics I\n" +
            "Capital Liquid Cooled Electronics II\n" +
            "Capital Memetic Algorithm Bank I\n" +
            "Capital Memetic Algorithm Bank II\n" +
            "Capital Signal Disruption Amplifier I\n" +
            "Capital Signal Disruption Amplifier II\n" +
            "Capital Inverted Signal Field Projector I\n" +
            "Capital Inverted Signal Field Projector II\n" +
            "Capital Ionic Field Projector I\n" +
            "Capital Ionic Field Projector II\n" +
            "Capital Particle Dispersion Augmentor I\n" +
            "Capital Particle Dispersion Augmentor II\n" +
            "Capital Particle Dispersion Projector I\n" +
            "Capital Particle Dispersion Projector II\n" +
            "Capital Signal Focusing Kit I\n" +
            "Capital Signal Focusing Kit II\n" +
            "Capital Targeting System Subcontroller I\n" +
            "Capital Targeting System Subcontroller II\n" +
            "Capital Targeting Systems Stabilizer I\n" +
            "Capital Targeting Systems Stabilizer II\n" +
            "Capital Tracking Diagnostic Subroutines I\n" +
            "Capital Tracking Diagnostic Subroutines II\n" +
            "Capital Ancillary Current Router I\n" +
            "Capital Ancillary Current Router II\n" +
            "Capital Capacitor Control Circuit I\n" +
            "Capital Capacitor Control Circuit II\n" +
            "Capital Egress Port Maximizer I\n" +
            "Capital Egress Port Maximizer II\n" +
            "Capital Powergrid Subroutine Maximizer I\n" +
            "Capital Powergrid Subroutine Maximizer II\n" +
            "Capital Semiconductor Memory Cell I\n" +
            "Capital Semiconductor Memory Cell II\n" +
            "Capital Algid Energy Administrations Unit I\n" +
            "Capital Algid Energy Administrations Unit II\n" +
            "Capital Energy Ambit Extension I\n" +
            "Capital Energy Ambit Extension II\n" +
            "Capital Energy Burst Aerator I\n" +
            "Capital Energy Burst Aerator II\n" +
            "Capital Energy Collision Accelerator I\n" +
            "Capital Energy Collision Accelerator II\n" +
            "Capital Energy Discharge Elutriation I\n" +
            "Capital Energy Discharge Elutriation II\n" +
            "Capital Energy Locus Coordinator I\n" +
            "Capital Energy Locus Coordinator II\n" +
            "Capital Energy Metastasis Adjuster I\n" +
            "Capital Energy Metastasis Adjuster II\n" +
            "Capital Algid Hybrid Administrations Unit I\n" +
            "Capital Algid Hybrid Administrations Unit II\n" +
            "Capital Hybrid Ambit Extension I\n" +
            "Capital Hybrid Ambit Extension II\n" +
            "Capital Hybrid Burst Aerator I\n" +
            "Capital Hybrid Burst Aerator II\n" +
            "Capital Hybrid Collision Accelerator I\n" +
            "Capital Hybrid Collision Accelerator II\n" +
            "Capital Hybrid Discharge Elutriation I\n" +
            "Capital Hybrid Discharge Elutriation II\n" +
            "Capital Hybrid Locus Coordinator I\n" +
            "Capital Hybrid Locus Coordinator II\n" +
            "Capital Hybrid Metastasis Adjuster I\n" +
            "Capital Hybrid Metastasis Adjuster II\n" +
            "Capital Bay Loading Accelerator I\n" +
            "Capital Bay Loading Accelerator II\n" +
            "Capital Hydraulic Bay Thrusters I\n" +
            "Capital Rocket Fuel Cache Partition I\n" +
            "Capital Rocket Fuel Cache Partition II\n" +
            "Capital Warhead Calefaction Catalyst I\n" +
            "Capital Warhead Calefaction Catalyst II\n" +
            "Capital Warhead Flare Catalyst I\n" +
            "Capital Warhead Flare Catalyst II\n" +
            "Capital Warhead Rigor Catalyst I\n" +
            "Capital Warhead Rigor Catalyst II\n" +
            "Capital Projectile Ambit Extension I\n" +
            "Capital Projectile Ambit Extension II\n" +
            "Capital Projectile Burst Aerator I\n" +
            "Capital Projectile Burst Aerator II\n" +
            "Capital Projectile Collision Accelerator I\n" +
            "Capital Projectile Collision Accelerator II\n" +
            "Capital Projectile Locus Coordinator I\n" +
            "Capital Projectile Locus Coordinator II\n" +
            "Capital Projectile Metastasis Adjuster I\n" +
            "Capital Projectile Metastasis Adjuster II\n" +
            "Capital Anti-EM Screen Reinforcer I\n" +
            "Capital Anti-EM Screen Reinforcer II\n" +
            "Capital Anti-Explosive Screen Reinforcer I\n" +
            "Capital Anti-Explosive Screen Reinforcer II\n" +
            "Capital Anti-Kinetic Screen Reinforcer I\n" +
            "Capital Anti-Kinetic Screen Reinforcer II\n" +
            "Capital Anti-Thermal Screen Reinforcer I\n" +
            "Capital Anti-Thermal Screen Reinforcer II\n" +
            "Capital Core Defense Capacitor Safeguard I\n" +
            "Capital Core Defense Capacitor Safeguard II\n" +
            "Capital Core Defense Charge Economizer I\n" +
            "Capital Core Defense Charge Economizer II\n" +
            "Capital Core Defense Field Extender I\n" +
            "Capital Core Defense Field Extender II\n" +
            "Capital Core Defense Field Purger I\n" +
            "Capital Core Defense Field Purger II\n" +
            "Capital Core Defense Operational Solidifier I\n" +
            "Capital Core Defense Operational Solidifier II\n" +
            "Cyclops I\n" +
            "Malleus I\n" +
            "Tyrfing I\n" +
            "Mantis I\n" +
            "X-Large Ancillary Shield Booster\n" +
            "Unit D-34343's Modified Fighter Support Unit\n" +
            "Unit F-435454's Modified Fighter Support Unit\n" +
            "Unit P-343554's Modified Fighter Support Unit\n" +
            "Unit W-634's Modified Fighter Support Unit\n" +
            "Capital Drone Control Range Augmentor I\n" +
            "Capital Drone Control Range Augmentor II\n" +
            "Capital Drone Durability Enhancer I\n" +
            "Capital Drone Durability Enhancer II\n" +
            "Capital Drone Mining Augmentor I\n" +
            "Capital Drone Mining Augmentor II\n" +
            "Capital Drone Repair Augmentor I\n" +
            "Capital Drone Repair Augmentor II\n" +
            "Capital Drone Scope Chip I\n" +
            "Capital Drone Scope Chip II\n" +
            "Capital Drone Speed Augmentor I\n" +
            "Capital Drone Speed Augmentor II\n" +
            "Capital Hydraulic Bay Thrusters II\n" +
            "Capital Processor Overclocking Unit I\n" +
            "Capital Processor Overclocking Unit II\n" +
            "Capital Sentry Damage Augmentor I\n" +
            "Capital Sentry Damage Augmentor II\n" +
            "Capital Stasis Drone Augmentor I\n" +
            "Capital Stasis Drone Augmentor II\n" +
            "Capital Transverse Bulkhead I\n" +
            "Capital Transverse Bulkhead II\n" +
            "Capital Higgs Anchor I\n" +
            "Quad 800mm Repeating Cannon I\n" +
            "Quad Mega Pulse Laser I\n" +
            "Triple Neutron Blaster Cannon I\n" +
            "Quad Mega Pulse Laser II\n" +
            "Modulated Compact Quad Mega Pulse Laser\n" +
            "Dual Giga Pulse Laser II\n" +
            "Dual Giga Beam Laser II\n" +
            "Triple Neutron Blaster Cannon II\n" +
            "Regulated Compact Triple Neutron Blaster Cannon\n" +
            "Ion Siege Blaster II\n" +
            "Dual 1000mm Railgun II\n" +
            "Quad 800mm Repeating Cannon II\n" +
            "Compact Carbine Quad 800mm Repeating Cannon\n" +
            "Hexa 2500mm Repeating Cannon II\n" +
            "Quad 3500mm Siege Artillery II\n" +
            "Cenobite I\n" +
            "Scarab I\n" +
            "Siren I\n" +
            "Dromi I\n" +
            "Capital Shield Extender I\n" +
            "Capital Azeotropic Restrained Shield Extender\n" +
            "Capital F-S9 Regolith Compact Shield Extender\n" +
            "Capital Shield Extender II\n" +
            "Equite I\n" +
            "Locust I\n" +
            "Satyr I\n" +
            "Gram I\n" +
            "Ametat I\n" +
            "Termite I\n" +
            "Antaeus I\n" +
            "Gungnir I\n" +
            "Equite II\n" +
            "Gram II\n" +
            "Locust II\n" +
            "Satyr II\n" +
            "Templar II\n" +
            "Dragonfly II\n" +
            "Firbolg II\n" +
            "Einherji II\n" +
            "Ametat II\n" +
            "Malleus II\n" +
            "Antaeus II\n" +
            "Cyclops II\n" +
            "Gungnir II\n" +
            "Tyrfing II\n" +
            "Termite II\n" +
            "Mantis II\n" +
            "Cenobite II\n" +
            "Scarab II\n" +
            "Siren II\n" +
            "Dromi II\n" +
            "Stasis Webification Burst Projector\n" +
            "Energy Neutralization Burst Projector\n" +
            "Capital Energy Neutralizer I\n" +
            "Capital Gremlin Compact Energy Neutralizer\n" +
            "Capital Infectious Scoped Energy Neutralizer\n" +
            "Capital Energy Neutralizer II\n" +
            "True Sansha Capital Energy Neutralizer\n" +
            "Dark Blood Capital Energy Neutralizer\n" +
            "Capital Energy Nosferatu I\n" +
            "Capital Ghoul Compact Energy Nosferatu\n" +
            "Capital Knave Scoped Energy Nosferatu\n" +
            "Capital Energy Nosferatu II\n" +
            "True Sansha Capital Energy Nosferatu\n" +
            "Dark Blood Capital Energy Nosferatu\n" +
            "Warp Disruption Burst Projector\n" +
            "Sensor Dampening Burst Projector\n" +
            "Target Illumination Burst Projector\n" +
            "Weapon Disruption Burst Projector\n" +
            "Superweapon_AOEGuide\n" +
            "Capital Emergency Hull Energizer I\n" +
            "Capital Implacable Compact Emergency Hull Energizer\n" +
            "Capital Indefatigable Enduring Emergency Hull Energizer\n" +
            "Capital Emergency Hull Energizer II\n" +
            "Sisters Capital Emergency Hull Energizer\n" +
            "Modal Enduring Quad Mega Pulse Laser\n" +
            "Anode Scoped Quad Mega Pulse Laser\n" +
            "Afocal Precise Quad Mega Pulse Laser\n" +
            "Dark Blood Quad Mega Pulse Laser\n" +
            "True Sansha Quad Mega Pulse Laser\n" +
            "Quad Mega Pulse Laser I Blueprint\n" +
            "Modulated Compact Quad Mega Pulse Laser Blueprint\n" +
            "Modal Enduring Quad Mega Pulse Laser Blueprint\n" +
            "Anode Scoped Quad Mega Pulse Laser Blueprint\n" +
            "Afocal Precise Quad Mega Pulse Laser Blueprint\n" +
            "Modal Enduring Triple Neutron Blaster Cannon\n" +
            "Anode Scoped Triple Neutron Blaster Cannon\n" +
            "Limited Precise Triple Neutron Blaster Cannon\n" +
            "Shadow Serpentis Triple Neutron Blaster Cannon\n" +
            "Ample Gallium Quad 800mm Repeating Cannon\n" +
            "Scout Scoped Quad 800mm Repeating Cannon\n" +
            "Prototype Precise Quad 800mm Repeating Cannon\n" +
            "Domination Quad 800mm Repeating Cannon\n" +
            "Triple Neutron Blaster Cannon I Blueprint\n" +
            "Regulated Compact Triple Neutron Blaster Cannon Blueprint\n" +
            "Modal Enduring Triple Neutron Blaster Cannon Blueprint\n" +
            "Anode Scoped Triple Neutron Blaster Cannon Blueprint\n" +
            "Limited Precise Triple Neutron Blaster Cannon Blueprint\n" +
            "Quad 800mm Repeating Cannon I Blueprint\n" +
            "Compact Carbine Quad 800mm Repeating Cannon Blueprint\n" +
            "Ample Gallium Quad 800mm Repeating Cannon Blueprint\n" +
            "Scout Scoped Quad 800mm Repeating Cannon Blueprint\n" +
            "Prototype Precise Quad 800mm Repeating Cannon Blueprint\n" +
            "Modulated Compact Dual Giga Pulse Laser\n" +
            "Modal Enduring Dual Giga Pulse Laser\n" +
            "Anode Scoped Dual Giga Pulse Laser\n" +
            "Afocal Precise Dual Giga Pulse Laser\n" +
            "Dark Blood Dual Giga Pulse Laser\n" +
            "True Sansha Dual Giga Pulse Laser\n" +
            "Modulated Compact Dual Giga Pulse Laser Blueprint\n" +
            "Modal Enduring Dual Giga Pulse Laser Blueprint\n" +
            "Anode Scoped Dual Giga Pulse Laser Blueprint\n" +
            "Afocal Precise Dual Giga Pulse Laser Blueprint\n" +
            "Modulated Compact Dual Giga Beam Laser\n" +
            "Modal Enduring Dual Giga Beam Laser\n" +
            "Anode Scoped Dual Giga Beam Laser\n" +
            "Afocal Precise Dual Giga Beam Laser\n" +
            "Dark Blood Dual Giga Beam Laser\n" +
            "True Sansha Dual Giga Beam Laser\n" +
            "Modulated Compact Dual Giga Beam Laser Blueprint\n" +
            "Modal Enduring Dual Giga Beam Laser Blueprint\n" +
            "Anode Scoped Dual Giga Beam Laser Blueprint\n" +
            "Afocal Precise Dual Giga Beam Laser Blueprint\n" +
            "Regulated Compact Ion Siege Blaster\n" +
            "Modal Enduring Ion Siege Blaster\n" +
            "Anode Scoped Ion Siege Blaster\n" +
            "Limited Precise Ion Siege Blaster\n" +
            "Shadow Serpentis Ion Siege Blaster\n" +
            "Regulated Compact Ion Siege Blaster Blueprint\n" +
            "Modal Enduring Ion Siege Blaster Blueprint\n" +
            "Anode Scoped Ion Siege Blaster Blueprint\n" +
            "Limited Precise Ion Siege Blaster Blueprint\n" +
            "Carbide Compact Dual 1000mm Railgun\n" +
            "Compressed Enduring Dual 1000mm Railgun\n" +
            "Scout Scoped Dual 1000mm Railgun\n" +
            "Prototype Precise Dual 1000mm Railgun\n" +
            "Shadow Serpentis Dual 1000mm Railgun\n" +
            "Carbide Compact Dual 1000mm Railgun Blueprint\n" +
            "Compressed Enduring Dual 1000mm Railgun Blueprint\n" +
            "Scout Scoped Dual 1000mm Railgun Blueprint\n" +
            "Prototype Precise Dual 1000mm Railgun Blueprint\n" +
            "Carbine Compact Hexa 2500mm Repeating Cannon\n" +
            "Gallium Ample Hexa 2500mm Repeating Cannon\n" +
            "Scout Scoped Hexa 2500mm Repeating Cannon\n" +
            "Prototype Precise Hexa 2500mm Repeating Cannon\n" +
            "Domination Hexa 2500mm Repeating Cannon\n" +
            "Carbide Compact Quad 3500mm Siege Artillery\n" +
            "Gallium Ample Quad 3500mm Siege Artillery\n" +
            "Scout Scoped Quad 3500mm Siege Artillery\n" +
            "Prototype Precise Quad 3500mm Siege Artillery\n" +
            "Domination Quad 3500mm Siege Artillery\n" +
            "Carbine Compact Hexa 2500mm Repeating Cannon Blueprint\n" +
            "Gallium Ample Hexa 2500mm Repeating Cannon Blueprint\n" +
            "Scout Scoped Hexa 2500mm Repeating Cannon Blueprint\n" +
            "Prototype Precise Hexa 2500mm Repeating Cannon Blueprint\n" +
            "Carbide Compact Quad 3500mm Siege Artillery Blueprint\n" +
            "Gallium Ample Quad 3500mm Siege Artillery Blueprint\n" +
            "Scout Scoped Quad 3500mm Siege Artillery Blueprint\n" +
            "Prototype Precise Quad 3500mm Siege Artillery Blueprint\n" +
            "10000MN Afterburner I\n" +
            "10000MN Y-S8 Compact Afterburner\n" +
            "10000MN Monopropellant Enduring Afterburner\n" +
            "10000MN Afterburner II\n" +
            "Domination 10000MN Afterburner\n" +
            "Shadow Serpentis 10000MN Afterburner\n" +
            "50000MN Microwarpdrive I\n" +
            "50000MN Y-T8 Compact Microwarpdrive\n" +
            "50000MN Quad LiF Restrained Microwarpdrive\n" +
            "50000MN Cold-Gas Enduring Microwarpdrive\n" +
            "50000MN Microwarpdrive II\n" +
            "Domination 50000MN Microwarpdrive\n" +
            "Shadow Serpentis 50000MN Microwarpdrive\n" +
            "Hail XL\n" +
            "Barrage XL\n" +
            "Tremor XL\n" +
            "Quake XL\n" +
            "Void XL\n" +
            "Null XL\n" +
            "Javelin XL\n" +
            "Spike XL\n" +
            "Scorch XL\n" +
            "Conflagration XL\n" +
            "Gleam XL\n" +
            "Aurora XL\n" +
            "Superweapon_AOEECM\n" +
            "Networked Sensor Array\n" +
            "Hermes Compact Fighter Support Unit\n" +
            "Fighter Support Unit II\n" +
            "Sentient Fighter Support Unit\n" +
            "CONCORD Capital Shield Extender\n" +
            "True Sansha Capital Shield Extender\n" +
            "Dread Guristas Capital Shield Extender\n" +
            "Domination Capital Shield Extender\n" +
            "Capital I-ax Enduring Remote Armor Repairer\n" +
            "Capital Coaxial Compact Remote Armor Repairer\n" +
            "Capital Solace Scoped Remote Armor Repairer\n" +
            "Capital Remote Armor Repairer II\n" +
            "Dark Blood Capital Remote Armor Repairer\n" +
            "Shadow Serpentis Capital Remote Armor Repairer\n" +
            "Capital Asymmetric Enduring Remote Shield Booster\n" +
            "Capital Murky Compact Remote Shield Booster\n" +
            "Capital S95a Scoped Remote Shield Booster\n" +
            "True Sansha Capital Remote Shield Booster\n" +
            "Dread Guristas Capital Remote Shield Booster\n" +
            "Domination Capital Remote Shield Booster\n" +
            "Capital Remote Hull Repairer II\n" +
            "Capital Ancillary Remote Armor Repairer\n" +
            "Capital Ancillary Remote Shield Booster\n" +
            "Capital Cap Battery I\n" +
            "Capital Compact Pb-Acid Cap Battery\n" +
            "Capital Cap Battery II\n" +
            "Domination Capital Cap Battery\n" +
            "Dark Blood Capital Cap Battery\n" +
            "Capital Capacitor Booster I\n" +
            "Capital F-RX Compact Capacitor Booster\n" +
            "Capital Capacitor Booster II\n" +
            "Dark Blood Capital Capacitor Booster\n" +
            "True Sansha Capital Capacitor Booster\n" +
            "Capital I-a Enduring Armor Repairer\n" +
            "Capital ACM Compact Armor Repairer\n" +
            "Capital Armor Repairer II\n" +
            "Dark Blood Capital Armor Repairer\n" +
            "Shadow Serpentis Capital Armor Repairer\n" +
            "Capital Ancillary Armor Repairer\n" +
            "Capital Ancillary Shield Booster\n" +
            "Capital C-5L Compact Shield Booster\n" +
            "Capital Clarity Ward Enduring Shield Booster\n" +
            "Capital Shield Booster II\n" +
            "True Sansha Capital Shield Booster\n" +
            "Dread Guristas Capital Shield Booster\n" +
            "Domination Capital Shield Booster\n" +
            "Capital Hull Repairer I\n" +
            "Capital I-b Enduring Hull Repairer\n" +
            "Capital IEF Compact Hull Repairer\n" +
            "Capital Hull Repairer II\n" +
            "Capital Flex Armor Hardener I\n" +
            "Capital Flex Shield Hardener I\n" +
            "Capital Flex Armor Hardener II\n" +
            "Dark Blood Capital Flex Armor Hardener\n" +
            "Shadow Serpentis Capital Flex Armor Hardener\n" +
            "Capital Flex Shield Hardener II\n" +
            "True Sansha Capital Flex Shield Hardener\n" +
            "Dread Guristas Capital Flex Shield Hardener\n" +
            "Domination Capital Flex Shield Hardener\n" +
            "Capital Radiative Scoped Remote Capacitor Transmitter\n" +
            "Capital Inductive Compact Remote Capacitor Transmitter";
}
