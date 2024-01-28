import React, {useState, useEffect} from 'react';
import {Accordion} from '@hilla/react-components/Accordion.js';
import {AccordionPanel} from '@hilla/react-components/AccordionPanel.js';
import {VerticalLayout} from '@hilla/react-components/VerticalLayout';
import {Button} from '@hilla/react-components/Button.js';
import {TradeService, UserEndpoint, UserService} from 'Frontend/generated/endpoints';
import User from 'Frontend/generated/ch/heigvd/application/data/entities/User';
import {ChartSeries} from "@hilla/react-components/ChartSeries";
import {Chart} from "@hilla/react-components/Chart";
import {Avatar} from "@hilla/react-components/Avatar";
import {Grid} from "@hilla/react-components/Grid";
import {GridColumn} from "@hilla/react-components/GridColumn";
import Trade from "Frontend/generated/ch/heigvd/application/data/entities/Trade";
import TradeType from "Frontend/generated/ch/heigvd/application/data/entities/TradeType";
import CryptoHoldingDto from "Frontend/generated/ch/heigvd/application/data/dto/CryptoHoldingDto";
import {IntegerField, IntegerFieldChangeEvent} from "@hilla/react-components/IntegerField";
import {useAuth} from "Frontend/util/auth";
import {Notification} from '@hilla/react-components/Notification.js';
import {EndpointError} from "@hilla/frontend";

export default function AccountView() {
    const gridRef = React.useRef<any>(null);
    const [currentUser, setCurrentUser] = useState<User>();
    const [ownedCryptos, setOwnedCryptos] = useState<CryptoHoldingDto[]>();
    const [myTrades, setMyTrades] = useState<Trade[]>();
    const [fundValue, setFundValue] = useState<number>(0);
    const {state} = useAuth();

    useEffect(() => {
        UserEndpoint.getAuthenticatedUser().then((user) => {
            setCurrentUser(user);
        });

        // Fetch cryptocurrencies data from the backend for the current user
        TradeService.getCryptoHoldings().then((ownedCrypto) => {
            setOwnedCryptos(ownedCrypto);
        });

    }, []);
    useEffect(() => {
        TradeService.getTrades().then((trades) => {
            setMyTrades(trades);
        });

        // Workaround for column width recalculation
        setTimeout(() => {
            gridRef.current?.recalculateColumnWidths();
        }, 100);
    }, []);

    useEffect(() => {
        // Check if currentUser has been updated and trigger any actions accordingly
        if (currentUser) {
            // Perform any actions based on the updated currentUser
        }
    }, [currentUser]);
    const handleFundUpdate = () => {
        if (currentUser) {
            const updatedFund = currentUser.funds + fundValue;

            // Update the fund value in the component state
            setCurrentUser({...currentUser, funds: updatedFund});

            // Update the fund value using the UserService
            UserService.updateFund(
                currentUser?.id || 0,
                updatedFund
            ).then(r => {
                // Display a success notification
                Notification.show(`Successfully updated fund to ${updatedFund}`);
            }).catch((error: EndpointError) => {
                // Display an error notification
                Notification.show(`Error during fund update: ${error.message}`, {theme: 'error'});
            });
        }
    }
    const handleFundChange = (e: IntegerFieldChangeEvent) => {
        setFundValue(parseInt(e.target.value));
    };

    function calculateCurrentValue(symbol: string | undefined, quantity: number, trades: Trade[] | undefined): number {
        if (!symbol || !trades) {
            //TODO remove at the end
            console.error('Symbol or trades not defined');
            return 0;
        }

        // Filter trades for the given symbol
        const symbolTrades = trades.filter((trade) => trade.cryptoCurrency?.symbol === symbol);

        // Calculate the current value based on buy and sell trades
        let currentValue = symbolTrades.reduce((totalValue, trade) => {
            if (trade.type === TradeType.BUY) {
                totalValue += trade.price * trade.quantity;
            } else if (trade.type === TradeType.SELL) {
                totalValue -= trade.price * trade.quantity;
            }
            return totalValue;
        }, 0);
        currentValue += quantity

        return currentValue;
    }

    const tradeTypeRenderer = (trade: Trade) => {
        return <span>
        {trade?.type ? trade?.type === TradeType.BUY ? "BUY" : "SELL" : "-"}
    </span>

    };
    const tradeSymboleRenderer = (trade: Trade) => {
        return <span>{trade?.cryptoCurrency?.symbol || '-'}</span>;

    };
    const tradePriceRenderer = (trade: Trade) => {
        return <span>{trade?.price || '-'}</span>;
    };

    const tradeDateRenderer = (trade: Trade) => {
        return <span>{trade?.date || '-'}</span>;
    };

    return (
        <Accordion>
            <AccordionPanel summary="Manage Fund">
                <VerticalLayout>
                    <Avatar name={`${currentUser?.firstName} ${currentUser?.lastName}`}/>
                    <span> Fund : {state.user?.funds}</span>
                    <IntegerField
                        label="Fund"
                        helperText="Add/withdrow"
                        min={0}
                        max={10000000}
                        value={fundValue.toString()}
                        stepButtonsVisible
                        onChange={(e) => handleFundChange(e)}
                    />
                    <Button onClick={handleFundUpdate}>Update Fund</Button>
                </VerticalLayout>
            </AccordionPanel>
            <AccordionPanel summary="History of trades">
                <Grid items={myTrades} allRowsVisible ref={gridRef}>
                    <GridColumn
                        header="Trade Type" flexGrow={0} autoWidth>
                        {({item}) => tradeTypeRenderer(item)}
                    </GridColumn>
                    <GridColumn
                        header="Crypto Symbol" flexGrow={0} autoWidth>
                        {({item}) => tradeSymboleRenderer(item)}
                    </GridColumn>
                    <GridColumn path="quantity" flexGrow={0} autoWidth/>
                    <GridColumn
                        header="Trade Price" flexGrow={0} autoWidth>
                        {({item}) => tradePriceRenderer(item)}
                    </GridColumn>
                    <GridColumn
                        header="date" flexGrow={0} autoWidth>
                        {({item}) => tradeDateRenderer(item)}
                    </GridColumn>
                </Grid>
            </AccordionPanel>
            <AccordionPanel summary="Crypto chart ">
                <Chart type="pie" title="Owned Cryptos" tooltip>
                    <ChartSeries
                        title="Cryptos"
                        values={ownedCryptos?.map((ownedCrypto) => {
                            const symbol = ownedCrypto.cryptoCurrency?.symbol;
                            const quantity = ownedCrypto.quantity || 0;

                            // Get the current exchange rate or price for the cryptocurrency in USD //TODO change with usd value
                            const cryptoPriceInUSD = ownedCrypto.cryptoCurrency?.lastPrice || 10000;

                            // Calculate the current value in USD based on historical trades
                            const currentValueUSD = calculateCurrentValue(symbol, quantity, myTrades) * cryptoPriceInUSD;

                            console.log('Chart Data:', {name: symbol, y: currentValueUSD}); // tODO remove at the end Log the data

                            return {
                                name: symbol,
                                y: currentValueUSD,
                            };
                        })}
                    />
                </Chart>

            </AccordionPanel>

        </Accordion>
    );
}
