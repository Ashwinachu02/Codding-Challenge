package com.springboot.automob.service;

import java.time.Year;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.automob.enums.PolicyType;
import com.springboot.automob.enums.VehicleCondition;
import com.springboot.automob.model.Policy;
import com.springboot.automob.model.Vehicle;
import com.springboot.automob.repository.PolicyRepository;

@Service
public class PolicyService {
	
	@Autowired
    private PolicyRepository policyRepository;
	
	 public Policy calculatePremium(Vehicle vehicle, String policyType) {
		 int currentYear = Year.now().getValue();
		 int vehicleAge = currentYear - vehicle.getManufacturingYear();
		 double basePremium = 0;
		 double evDiscount = 0;
		 double usedSurcharge = 0;
		 double previousClaimsSurcharge = 0;
		 if (policyType.equals("COMPREHENSIVE")) {
	            basePremium = vehicle.getBasePrice() * 0.05; // comprehensive
	     } else if (policyType.equals("THIRD_PARTY")) {
	            basePremium = vehicle.getBasePrice() * 0.03; // third party
	     }
		 	
		 double premium = basePremium;
		 
		 double ageSurcharge = 0;
	        if (vehicleAge >= 1 && vehicleAge <= 3) {
	            ageSurcharge = basePremium * 0.05; //  1-3 years
	        } else if (vehicleAge >= 4 && vehicleAge <= 6) {
	            ageSurcharge = basePremium * 0.10; //  4-6 years
	        } else if (vehicleAge > 6) {
	            ageSurcharge = basePremium * 0.20; //  6 years
	        }
	        
	        if (vehicle.getVehicleCondition().equals(VehicleCondition.USED)) {
	            usedSurcharge = basePremium * 0.10; //  vehicles
	        }
		 
	        double zoneAdjustment = 0;
	        switch (vehicle.getZoneType()) {
	            case ZONE_A:
	                zoneAdjustment = basePremium * 0.15; // Zone A
	                break;
	            case ZONE_B:
	                zoneAdjustment = basePremium * 0.10;// Zone B
	                break;
	            case ZONE_C:
	                zoneAdjustment = basePremium * 0.05; //  Zone C
	                break;
	        }
	        
	        double fuelTransmissionAdjustment = 0;
			if (vehicle.getVehicleType().equals("CAR")) {//car
				if (vehicle.getTransmissionType().equals("Automatic")) {//automatic
					fuelTransmissionAdjustment += basePremium * 0.05; 

					if (vehicle.getFuelType().equals("Petrol")) {
						fuelTransmissionAdjustment += basePremium * 0.03;//automatic+petrol
					} else if (vehicle.getFuelType().equals("Diesel")) {
						fuelTransmissionAdjustment += basePremium * 0.05; //automatic+diesel
					}
				} else if (vehicle.getTransmissionType().equals("Manual")) {
					if (vehicle.getFuelType().equals("Petrol")) {
						fuelTransmissionAdjustment += basePremium * 0.02; //petrol+ manual
					} else if (vehicle.getFuelType().equals("Diesel")) {
						fuelTransmissionAdjustment += basePremium * 0.03; // diesel+ manual
					}
				}
			} else if (vehicle.getVehicleType().equals("BIKE")) {
				// Bike specific fuel and transmission adjustments
				if (vehicle.getTransmissionType().equals("Geared")) {
					fuelTransmissionAdjustment += basePremium * 0.03;// geared bikes

					if (vehicle.getFuelType().equals("Petrol")) {
						fuelTransmissionAdjustment += basePremium * 0.02; //  petrol geared bikes
					}
				} else if (vehicle.getTransmissionType().equals("Non-Geared")) {
					if (vehicle.getFuelType().equals("Petrol")) {
						fuelTransmissionAdjustment += basePremium * 0.01; // petrol non-geared bikes
					}
				}
			}
	        
	        if (vehicle.getFuelType().equals("EV")) {
	            evDiscount = basePremium * 0.10; //EV
	        }
	        
	        if (vehicle.isPreviousClaim()) {
	            previousClaimsSurcharge = basePremium * 0.20; // previous claims
	        }
	        
	        
	        
	        /*
	         * Calculating Coverage Amount
	         */
	        
	        double coverageAmount;
	        if (policyType.equals("COMPREHENSIVE")) {
	            coverageAmount = vehicle.getBasePrice() * 0.80;
	        } else if (policyType.equals("THIRD_PARTY")) {
	            coverageAmount = vehicle.getBasePrice() * 0.50; 
	        }else {
	        	throw new IllegalArgumentException("Invalid policy type");
	        }
	        
	        
	        
	        premium = basePremium + ageSurcharge + usedSurcharge + zoneAdjustment + fuelTransmissionAdjustment + previousClaimsSurcharge - evDiscount;
	        Policy policy = new Policy();
	        policy.setPolicyType(PolicyType.valueOf(policyType));
	        policy.setPremiumAmount(premium);
	        policy.setCoverageAmount(coverageAmount);
	        policy.setTermLength(12); 
	        policy.setPolicyStatus(null); 
	        
	        return policy;



	 }
}
