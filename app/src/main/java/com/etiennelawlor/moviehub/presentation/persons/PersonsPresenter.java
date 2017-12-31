package com.etiennelawlor.moviehub.presentation.persons;

import com.etiennelawlor.moviehub.data.network.response.Person;
import com.etiennelawlor.moviehub.data.repositories.models.PersonsDataModel;
import com.etiennelawlor.moviehub.domain.PersonsDomainContract;
import com.etiennelawlor.moviehub.util.NetworkUtility;
import com.etiennelawlor.moviehub.util.rxjava.ProductionSchedulerTransformer;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;

/**
 * Created by etiennelawlor on 2/9/17.
 */

public class PersonsPresenter implements PersonsUiContract.Presenter {

    // region Member Variables
    private final PersonsUiContract.View personsView;
    private final PersonsDomainContract.UseCase personsUseCase;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    // endregion

    // region Constructors
    public PersonsPresenter(PersonsUiContract.View personsView, PersonsDomainContract.UseCase personsUseCase) {
        this.personsView = personsView;
        this.personsUseCase = personsUseCase;
    }
    // endregion

    // region PersonsUiContract.Presenter Methods
    @Override
    public void onDestroyView() {
        if (compositeDisposable != null)
            compositeDisposable.clear();
    }

    @Override
    public void onLoadPopularPersons(final int currentPage) {
        if(currentPage == 1){
            personsView.hideEmptyView();
            personsView.hideErrorView();
            personsView.showLoadingView();
        } else{
            personsView.showLoadingFooter();
        }

        Disposable disposable = personsUseCase.getPopularPersons(currentPage)
//                .compose(schedulerTransformer)
                .compose(new ProductionSchedulerTransformer<PersonsDataModel>())
                .subscribeWith(new DisposableSingleObserver<PersonsDataModel>() {
                    @Override
                    public void onSuccess(PersonsDataModel personsDataModel) {
                        if(personsDataModel != null){
                            List<Person> persons = personsDataModel.getPersons();
                            int currentPage = personsDataModel.getPageNumber();
                            boolean isLastPage = personsDataModel.isLastPage();
                            boolean hasMovies = personsDataModel.hasPersons();

                            if(currentPage == 1){
                                personsView.hideLoadingView();

                                if(hasMovies){
                                    personsView.addHeader();
                                    personsView.addPersonsToAdapter(persons);

                                    if(!isLastPage)
                                        personsView.addFooter();
                                } else {
                                    personsView.showEmptyView();
                                }
                            } else {
                                personsView.removeFooter();

                                if(hasMovies){
                                    personsView.addPersonsToAdapter(persons);

                                    if(!isLastPage)
                                        personsView.addFooter();
                                }
                            }

                            personsView.setPersonsDataModel(personsDataModel);
                        }
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        throwable.printStackTrace();

                        if(currentPage == 1){
                            personsView.hideLoadingView();

                            if (NetworkUtility.isKnownException(throwable)) {
                                personsView.setErrorText("Can't load data.\nCheck your network connection.");
                                personsView.showErrorView();
                            }
                        } else {
                            if(NetworkUtility.isKnownException(throwable)){
                                personsView.showErrorFooter();
                            }
                        }
                    }
                });

        compositeDisposable.add(disposable);
    }

    @Override
    public void onPersonClick(Person person) {
        personsView.openPersonDetails(person);
    }

    @Override
    public void onScrollToEndOfList() {
        personsView.loadMoreItems();
    }
    // endregion
}
