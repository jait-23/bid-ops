(function() {
    'use strict';

    angular
        .module('bidopscoreApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('solicitation-reviewer', {
            parent: 'entity',
            url: '/solicitation-reviewer?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'bidopscoreApp.solicitationReviewer.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/solicitation-reviewer/solicitation-reviewers.html',
                    controller: 'SolicitationReviewerController',
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
                    $translatePartialLoader.addPart('solicitationReviewer');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('solicitation-reviewer-detail', {
            parent: 'solicitation-reviewer',
            url: '/solicitation-reviewer/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'bidopscoreApp.solicitationReviewer.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/solicitation-reviewer/solicitation-reviewer-detail.html',
                    controller: 'SolicitationReviewerDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('solicitationReviewer');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'SolicitationReviewer', function($stateParams, SolicitationReviewer) {
                    return SolicitationReviewer.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'solicitation-reviewer',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('solicitation-reviewer-detail.edit', {
            parent: 'solicitation-reviewer-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/solicitation-reviewer/solicitation-reviewer-dialog.html',
                    controller: 'SolicitationReviewerDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['SolicitationReviewer', function(SolicitationReviewer) {
                            return SolicitationReviewer.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('solicitation-reviewer.new', {
            parent: 'solicitation-reviewer',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/solicitation-reviewer/solicitation-reviewer-dialog.html',
                    controller: 'SolicitationReviewerDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                reviewerId: null,
                                solicitationId: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('solicitation-reviewer', null, { reload: 'solicitation-reviewer' });
                }, function() {
                    $state.go('solicitation-reviewer');
                });
            }]
        })
        .state('solicitation-reviewer.edit', {
            parent: 'solicitation-reviewer',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/solicitation-reviewer/solicitation-reviewer-dialog.html',
                    controller: 'SolicitationReviewerDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['SolicitationReviewer', function(SolicitationReviewer) {
                            return SolicitationReviewer.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('solicitation-reviewer', null, { reload: 'solicitation-reviewer' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('solicitation-reviewer.delete', {
            parent: 'solicitation-reviewer',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/solicitation-reviewer/solicitation-reviewer-delete-dialog.html',
                    controller: 'SolicitationReviewerDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['SolicitationReviewer', function(SolicitationReviewer) {
                            return SolicitationReviewer.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('solicitation-reviewer', null, { reload: 'solicitation-reviewer' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
