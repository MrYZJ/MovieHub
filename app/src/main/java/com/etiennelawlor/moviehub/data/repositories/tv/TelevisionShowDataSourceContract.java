package com.etiennelawlor.moviehub.data.repositories.tv;

import com.etiennelawlor.moviehub.data.network.response.TelevisionShow;
import com.etiennelawlor.moviehub.data.network.response.TelevisionShowContentRatingsEnvelope;
import com.etiennelawlor.moviehub.data.network.response.TelevisionShowCreditsEnvelope;
import com.etiennelawlor.moviehub.data.network.response.TelevisionShowsEnvelope;
import com.etiennelawlor.moviehub.data.repositories.models.TelevisionShowsDataModel;

import io.reactivex.Maybe;
import io.reactivex.Single;

/**
 * Created by etiennelawlor on 2/13/17.
 */

public interface TelevisionShowDataSourceContract {

    interface Repository {
        Single<TelevisionShowsDataModel> getPopularTelevisionShows(int currentPage);
        Single<TelevisionShow> getTelevisionShow(int tvId);
        Single<TelevisionShowCreditsEnvelope> getTelevisionShowCredits(int tvId);
        Single<TelevisionShowsEnvelope> getSimilarTelevisionShows(int tvId);
        Single<TelevisionShowContentRatingsEnvelope> getTelevisionShowContentRatings(int tvId);
    }

    interface LocalDateSource {
        Maybe<TelevisionShowsDataModel> getPopularTelevisionShows(int currentPage);

        void savePopularTelevisionShows(TelevisionShowsDataModel televisionShowsDataModel);

        Maybe<TelevisionShow> getTelevisionShow(int tvId);
        void saveTelevisionShow(TelevisionShow televisionShow);

        Maybe<TelevisionShowCreditsEnvelope> getTelevisionShowCredits(int tvId);
        void saveTelevisionShowCredits(TelevisionShowCreditsEnvelope televisionShowCreditsEnvelope);

        Maybe<TelevisionShowsEnvelope> getSimilarTelevisionShows(int tvId);
        void saveSimilarTelevisionShows(TelevisionShowsEnvelope televisionShowsEnvelope);

        Maybe<TelevisionShowContentRatingsEnvelope> getTelevisionShowContentRatings(int tvId);
        void saveTelevisionShowContentRatings(TelevisionShowContentRatingsEnvelope televisionShowContentRatingsEnvelope);
    }

    interface RemoteDateSource {
        Single<TelevisionShowsEnvelope> getPopularTelevisionShows(int currentPage);
        Single<TelevisionShow> getTelevisionShow(int tvId);
        Single<TelevisionShowCreditsEnvelope> getTelevisionShowCredits(int tvId);
        Single<TelevisionShowsEnvelope> getSimilarTelevisionShows(int tvId);
        Single<TelevisionShowContentRatingsEnvelope> getTelevisionShowContentRatings(int tvId);
    }
}
