(function() {
    'use strict';

    angular
        .module('bidopscoreApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('evaluator-one', {
            parent: 'entity',
            url: '/evaluator-one?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'bidopscoreApp.evaluatorOne.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/evaluator-one/evaluator-ones.html',
                    controller: 'EvaluatorOneController',
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
                    $translatePartialLoader.addPart('evaluatorOne');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('evaluator-one-detail', {
            parent: 'evaluator-one',
            url: '/evaluator-one/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'bidopscoreApp.evaluatorOne.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/evaluator-one/evaluator-one-detail.html',
                    controller: 'EvaluatorOneDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('evaluatorOne');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'EvaluatorOne', function($stateParams, EvaluatorOne) {
                    return EvaluatorOne.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'evaluator-one',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('evaluator-one-detail.edit', {
            parent: 'evaluator-one-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/evaluator-one/evaluator-one-dialog.html',
                    controller: 'EvaluatorOneDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['EvaluatorOne', function(EvaluatorOne) {
                            return EvaluatorOne.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('evaluator-one.new', {
            parent: 'evaluator-one',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/evaluator-one/evaluator-one-dialog.html',
                    controller: 'EvaluatorOneDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                userId: null,
                                solicitationId: null,
                                docURL: null,
                                categorySolicitation: null,
                                status: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('evaluator-one', null, { reload: 'evaluator-one' });
                }, function() {
                    $state.go('evaluator-one');
                });
            }]
        })
        .state('evaluator-one.edit', {
            parent: 'evaluator-one',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/evaluator-one/evaluator-one-dialog.html',
                    controller: 'EvaluatorOneDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['EvaluatorOne', function(EvaluatorOne) {
                            return EvaluatorOne.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('evaluator-one', null, { reload: 'evaluator-one' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('evaluator-one.delete', {
            parent: 'evaluator-one',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/evaluator-one/evaluator-one-delete-dialog.html',
                    controller: 'EvaluatorOneDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['EvaluatorOne', function(EvaluatorOne) {
                            return EvaluatorOne.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('evaluator-one', null, { reload: 'evaluator-one' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
