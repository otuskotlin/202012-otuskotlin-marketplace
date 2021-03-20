package ru.otus.otuskotlin.marketplace.backend.app.ktor

import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass
import ru.otus.otuskotlin.marketplace.transport.kmp.models.common.MpMessage
import ru.otus.otuskotlin.marketplace.transport.kmp.models.demands.*
import ru.otus.otuskotlin.marketplace.transport.kmp.models.proposals.*

val jsonConfig: Json by lazy {
    Json {
        prettyPrint = true
        serializersModule = SerializersModule {
            polymorphic(MpMessage::class) {
                subclass(MpRequestDemandCreate::class)
                subclass(MpRequestDemandRead::class)
                subclass(MpRequestDemandDelete::class)
                subclass(MpRequestDemandUpdate::class)
                subclass(MpRequestDemandList::class)
                subclass(MpRequestDemandOffersList::class)
                subclass(MpRequestProposalCreate::class)
                subclass(MpRequestProposalRead::class)
                subclass(MpRequestProposalDelete::class)
                subclass(MpRequestProposalUpdate::class)
                subclass(MpRequestProposalList::class)
                subclass(MpRequestProposalOffersList::class)
                subclass(MpResponseDemandCreate::class)
                subclass(MpResponseDemandRead::class)
                subclass(MpResponseDemandDelete::class)
                subclass(MpResponseDemandUpdate::class)
                subclass(MpResponseDemandList::class)
                subclass(MpResponseDemandOffersList::class)
                subclass(MpResponseProposalCreate::class)
                subclass(MpResponseProposalRead::class)
                subclass(MpResponseProposalUpdate::class)
                subclass(MpResponseProposalDelete::class)
                subclass(MpResponseProposalList::class)
                subclass(MpResponseProposalOffersList::class)
            }

        }
        classDiscriminator = "type"
    }
}
