(function() {
    'use strict';

    angular
        .module('bidopscoreApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('primary-evaluations', {
            parent: 'entity',
            url: '/primary-evaluations?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'bidopscoreApp.primaryEvaluations.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/primary-evaluations/primary-evaluations.html',
                    controller: 'PrimaryEvaluationsController',
                    controllerAs: 'vm'
                }
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,asc',
                    squash: true
                },
                search: null
            },
            resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                    };
                }],
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('primaryEvaluations');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('primary-evaluations-detail', {
            parent: 'primary-evaluations',
            url: '/primary-evaluations/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'bidopscoreApp.primaryEvaluations.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/primary-evaluations/primary-evaluations-detail.html',
                    controller: 'PrimaryEvaluationsDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('primaryEvaluations');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'PrimaryEvaluations', function($stateParams, PrimaryEvaluations) {
                    return PrimaryEvaluations.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'primary-evaluations',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('primary-evaluations-detail.edit', {
            parent: 'primary-evaluations-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/primary-evaluations/primary-evaluations-dialog.html',
                    controller: 'PrimaryEvaluationsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PrimaryEvaluations', function(PrimaryEvaluations) {
                            return PrimaryEvaluations.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('primary-evaluations.new', {
            parent: 'primary-evaluations',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/primary-evaluations/primary-evaluations-dialog.html',
                    controller: 'PrimaryEvaluationsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                userId: null,
                                solicitationId: null,
                                docURL: null,
                                categorySolicitations: null,
                                status: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('primary-evaluations', null, { reload: 'primary-evaluations' });
                }, function() {
                    $state.go('primary-evaluations');
                });
            }]
        })
        .state('primary-evaluations.edit', {
            parent: 'primary-evaluations',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/primary-evaluations/primary-evaluations-dialog.html',
                    controller: 'PrimaryEvaluationsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PrimaryEvaluations', function(PrimaryEvaluations) {
                            return PrimaryEvaluations.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('primary-evaluations', null, { reload: 'primary-evaluations' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('primary-evaluations.delete', {
            parent: 'primary-evaluations',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/primary-evaluations/primary-evaluations-delete-dialog.html',
                    controller: 'PrimaryEvaluationsDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['PrimaryEvaluations', function(PrimaryEvaluations) {
                            return PrimaryEvaluations.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('primary-evaluations', null, { reload: 'primary-evaluations' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
