import React, {useState, useEffect} from 'react';
import {useAuth} from 'Frontend/util/auth';
import {Accordion} from '@hilla/react-components/Accordion.js';
import {AccordionPanel} from '@hilla/react-components/AccordionPanel.js';
import {VerticalLayout} from '@hilla/react-components/VerticalLayout';
import {TextField} from '@hilla/react-components/TextField';
import {Button} from '@hilla/react-components/Button.js';
import {useForm} from '@hilla/react-form';
import UserModel from 'Frontend/generated/ch/heigvd/application/data/entities/UserModel';
import {CryptoCurrencyService, TradeService, UserEndpoint, UserService} from 'Frontend/generated/endpoints';
import User from 'Frontend/generated/ch/heigvd/application/data/entities/User';
import {ChartSeries} from "@hilla/react-components/ChartSeries";
import {Chart} from "@hilla/react-components/Chart";
import {Avatar} from "@hilla/react-components/Avatar";
import CryptoHoldingDto from "Frontend/generated/ch/heigvd/application/data/dto/CryptoHoldingDto";
import {Grid} from "@hilla/react-components/Grid";
import {GridColumn} from "@hilla/react-components/GridColumn";
import Trade from "Frontend/generated/ch/heigvd/application/data/entities/Trade";

export default function AccountView() {
    let [currentUser, setCurrentUser] = useState<User>();
    const [editMode, setEditMode] = useState(false);
    const {field, model, submit} = useForm(UserModel, {onSubmit}); // Define a form using the useForm hook
    const[cryptoData, setCryptoData] = useState<any[]>(); //TODO : fix this once the backend is done
    const[ownedCryptos, setOwnedCryptos] = useState<CryptoHoldingDto[]>();
    const[myTrades, setMyTrades] = useState<Trade[]>();

    useEffect(() => {
        UserEndpoint.getAuthenticatedUser().then((user) => {
            setCurrentUser(user);
        });

        // Fetch cryptocurrencies data from the backend for the current user
        // TODO implement getOwnedCryptoCurrencies in the backend
        TradeService.getCryptoHoldings().then((ownedCrypto) => {
            setOwnedCryptos(ownedCrypto);
        });
        TradeService.getTrades().then((trades) => {
            setMyTrades(trades);
        });
    }, []);

    async function onSubmit(updatedUser: User) {
        try {
            // Submit the updated user profile to the backend
            //TODO : fix this. The user is not updated in the backend, not allowed error
            await UserService.update(updatedUser);
            setCurrentUser(updatedUser);
            setEditMode(false); // Exit edit mode after successful update
        } catch (error) {
            console.error('Error during update:', error);
        }
    }

    return (
        <Accordion>
            <AccordionPanel summary="Personal information">
                {editMode ? (
                    // Render form in edit mode
                    <VerticalLayout>
                        <TextField label="Username" {...field(model.username)} />
                        <TextField label="First Name" {...field(model.firstName)} />
                        <TextField label="Last Name" {...field(model.lastName)} />
                        <TextField label="Funds" {...field(model.funds)} />

                        <div className="flex gap-m">
                            <Button onClick={async () => await submit() && setEditMode(false)} theme="primary">
                                Save
                            </Button>
                            <Button onClick={() => setEditMode(false)}>Cancel</Button>
                        </div>
                    </VerticalLayout>
                ) : (
                    // Render read-only view
                    <VerticalLayout>
                        <Avatar name={`${currentUser?.firstName} ${currentUser?.lastName}`} />
                        <span>Username: {currentUser?.username}</span>
                        <span>Firstname: {currentUser?.firstName}</span>
                        <span>Lastname: {currentUser?.lastName}</span>
                        <span>Funds: {currentUser?.funds}</span>

                        <Button onClick={() => setEditMode(true)}>Edit</Button>
                    </VerticalLayout>
                )}
            </AccordionPanel>
            <AccordionPanel summary="History of trades">
                <Grid items={myTrades} allRowsVisible>
                    <GridColumn path="type" />
                    <GridColumn path="quantity" />
                    <GridColumn path="price" />
                    <GridColumn path="date" />
                </Grid>
            </AccordionPanel>
            <AccordionPanel summary="Crypto chart ">
                <Chart type="pie" title="Owned Cryptos" tooltip>

                    <ChartSeries
                        title="Cryptos"
                        values={ownedCryptos?.map((ownedCrypto) => {
                            let cryptoPrice = ownedCrypto.cryptoCurrency?.lastPrice;
                            let owned = ownedCrypto.quantity? ownedCrypto.quantity : 0;
                            let amountInUSD = cryptoPrice? cryptoPrice * owned : 0;
                            return {
                                name: ownedCrypto.cryptoCurrency?.symbol,
                                value: amountInUSD
                            }
                        })}
                    />

                </Chart>
            </AccordionPanel>
        </Accordion>
    );
}
