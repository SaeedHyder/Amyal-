package com.app.amyal.entities
import com.google.gson.annotations.SerializedName

//PackageSerachModel

data class PackageSerachModel(
        var SourceLocationCode: String?,
        var DestinationLocationCode: String?,
        var CheckInTime: String?,
        var CheckOutTime: String?,
        var NoOfAdults: Int?,
        var NoOfChildrens: Int?,
        var NoOfInfant: Int?,
        var CurrencyCode: String?,
        var LanguageCode: String?,
        var CountryOfResidence: String?,
        var PackageCode: String?,
        var Duration: String?,
        var ratePlanCode: String?,
        var priceRange: String?,
        var sequenceNumber: String?
)

//getPackageList
data class PackageList(
		@SerializedName("SequenceNumber") val sequenceNumber: String?,
		@SerializedName("Results") val results: List<PackageListEnt?>?
)

data class PackageListEnt(
		@SerializedName("PackageName") val packageName: String?,
		@SerializedName("PackageCode") val packageCode: String?,
		@SerializedName("Descriptions") val description: String?,
		@SerializedName("RatePlanCode") val ratePlanCode: String?,
		@SerializedName("TotalAmount") val totalAmount: String?,
		@SerializedName("Images") val images: List<Any?>?,
		@SerializedName("CurrencyCode") val currencyCode: String?,
		@SerializedName("StartDate") val startDate: String?,
		@SerializedName("EndDate") val endDate: String?,
		@SerializedName("DestinationCode") val destinationCode: String?,
		@SerializedName("Destination") val destination: String?,
		@SerializedName("SourceCode") val sourceCode: String?,
		@SerializedName("Source") val source: String?,
		@SerializedName("Flights") val flightsList: List<FlightsList?>?,
		@SerializedName("Rating") val rating: String?,
		@SerializedName("TotalReviewCount") val totalReviewCount: String?,
		@SerializedName("TopReviews") val topReviews: List<TopReview?>?
)



//getPackageDetails
data class PackageDetailsEnt(
		@SerializedName("BookingCode") val bookingCode: String?,
		@SerializedName("ExpiryDate") val expiryDate: String?,
		@SerializedName("Packages") val packages: Packages?,
		@SerializedName("Hotels") val hotels: List<Hotel?>?,
		@SerializedName("TopReviews") val topReviews: List<TopReview?>?,
		@SerializedName("PackageDetail") val packageDetail: PackageDetail?
)

data class Hotel(
		@SerializedName("HotelName") val HotelName: String?,
		@SerializedName("CategoryCode") val categoryCode: String?,
		@SerializedName("Category") val category: String?,
		@SerializedName("Descriptions") val description: String?,
		@SerializedName("Thumb") val thumb: String?,
		@SerializedName("Address") val address: String?,
		@SerializedName("Latitude") val latitude: String?,
		@SerializedName("Longitude") val longitude: String?,
		@SerializedName("Rating") val rating: String?,
		@SerializedName("TotalReviewsCount") val totalReviewsCount: String?
)

data class PackageDetail(
		@SerializedName("PackageCode") val packageCode: String?,
		@SerializedName("PackageName") val packageName: String?,
		@SerializedName("Descriptions") val descriptions: List<Description?>?,
		@SerializedName("Images") val images: List<Any?>?,
		@SerializedName("Origins") val origins: List<Any?>?,
		@SerializedName("Destinations") val destinations: List<Destination?>?,
		@SerializedName("ProductTypes") val productTypes: List<String?>?,
		@SerializedName("Itineraries") val itineraries: List<Itinerary?>?,
		@SerializedName("StayDestinations") val stayDestinations: List<StayDestination?>?,
		@SerializedName("Duration") val duration: String?,
		@SerializedName("PriceRange") val priceRange: String?,
		@SerializedName("Currency") val currency: String?,
		@SerializedName("StartDate") val startDate: String?,
		@SerializedName("EndDate") val endDate: String?
)

data class Description(
		@SerializedName("Key") val key: String?,
		@SerializedName("Value") val value: String?
)

data class Destination(
		@SerializedName("Key") val key: String?,
		@SerializedName("Value") val value: String?
)

data class StayDestination(
		@SerializedName("Key") val key: String?,
		@SerializedName("Value") val value: String?
)

data class Itinerary(
		@SerializedName("Title") val Title: String?,
		@SerializedName("SubTitle") val SubTitle: String?,
		@SerializedName("Descriptions") val Description: String?,
		@SerializedName("Day") val Day: String?,
		@SerializedName("Duration") val Duration: String?,
		@SerializedName("DailyProductTypes") val dailyProductTypes: List<Any?>?
)

data class Packages(
		@SerializedName("statusField") val statusField: Int?,
		@SerializedName("statusFieldSpecified") val statusFieldSpecified: Boolean?,
		@SerializedName("packageCodeField") val packageCodeField: String?,
		@SerializedName("startField") val startField: String?,
		@SerializedName("endField") val endField: String?




)

