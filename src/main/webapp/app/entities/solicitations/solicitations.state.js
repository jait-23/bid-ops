(function() {
    'use strict';

    angular
        .module('bidopscoreApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('solicitations', {
            parent: 'entity',
            url: '/solicitations?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'bidopscoreApp.solicitations.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/solicitations/solicitations.html',
                    controller: 'SolicitationsController',
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
                    $translatePartialLoader.addPart('solicitations');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('solicitations-detail', {
            parent: 'solicitations',
            url: '/solicitations/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'bidopscoreApp.solicitations.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/solicitations/solicitations-detail.html',
                    controller: 'SolicitationsDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('solicitations');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Solicitations', function($stateParams, Solicitations) {
                    return Solicitations.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'solicitations',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('solicitations-detail1', {
            parent: 'solicitations',
            url: '/solicitations-detail1/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'bidopscoreApp.solicitations.detail.title'
            },
            views: {
            	 'appContent@home': {
                     templateUrl: 'app/entities/solicitations/solicitations-detail1.html',
                     controller: 'SolicitationsDetail1Controller',
                     controllerAs: 'vm'
            	 }
            },
                    resolve: {
                    	translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                            $translatePartialLoader.addPart('solicitations');
                            return $translate.refresh();
                        }],
                        entity: ['Solicitations', function(Solicitations) {
                            return Solicitations.get({id : $stateParams.id}).$promise;
                        }],
                        previousState: ["$state", function ($state) {
                            var currentStateData = {
                                name: $state.current.name || 'solicitations',
                                params: $state.params,
                                url: $state.href($state.current.name, $state.params)
                            };
                            return currentStateData;
                        }]
                    }
        })
        .state('solicitations.new', {
            parent: 'solicitations',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/solicitations/solicitations-dialog.html',
                    controller: 'SolicitationsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                solicitationId: null,
                                title: null,
                                status: null,
                                finalFilingDate: null,
                                lastUpdated: null,
                                type: null,
                                description: null,
                                category: null,
                                requiredDocuments: null,
                                reviewerDeliveryStatus: null,
                                approverStatus: null,
                                authorId: null,
                                reviewerId: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('solicitations', null, { reload: 'solicitations' });
                }, function() {
                    $state.go('solicitations');
                });
            }]
        })
        .state('solicitations.edit', {
            parent: 'solicitations',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/solicitations/solicitations-dialog.html',
                    controller: 'SolicitationsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Solicitations', function(Solicitations) {
                            return Solicitations.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('solicitations', null, { reload: 'solicitations' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('solicitations.delete', {
            parent: 'solicitations',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/solicitations/solicitations-delete-dialog.html',
                    controller: 'SolicitationsDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Solicitations', function(Solicitations) {
                            return Solicitations.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('solicitations', null, { reload: 'solicitations' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
