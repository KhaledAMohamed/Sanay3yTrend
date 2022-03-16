package apps.projectegy.sanay3ytrend.ui.activities.workers;


import apps.projectegy.sanay3ytrend.data.models.countries.GetAllCountriesResponse;
import apps.projectegy.sanay3ytrend.data.models.departments.GetDepartmentsResponse;
import apps.projectegy.sanay3ytrend.data.models.workers.GetWorkersResponse;

public interface WorkersInterface {
    void GetDepartmentsResponse(GetDepartmentsResponse allAdsMapResponse);

    void GetAllCountries(GetAllCountriesResponse getWorkersResponse);

    void GetWorkersResponse(GetWorkersResponse getWorkersResponse);
}


