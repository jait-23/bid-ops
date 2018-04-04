'use strict';

describe('Controller Tests', function() {

    describe('Bidders Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockBidders, MockSolicitations, MockJhi_user;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockBidders = jasmine.createSpy('MockBidders');
            MockSolicitations = jasmine.createSpy('MockSolicitations');
            MockJhi_user = jasmine.createSpy('MockJhi_user');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Bidders': MockBidders,
                'Solicitations': MockSolicitations,
                'Jhi_user': MockJhi_user
            };
            createController = function() {
                $injector.get('$controller')("BiddersDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'bidopscoreApp:biddersUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
